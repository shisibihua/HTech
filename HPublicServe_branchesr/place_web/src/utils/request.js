import fetch from 'dva/fetch';
import Promise from 'promise-polyfill';

if (!window.Promise) {
  window.Promise = Promise;
}
function parseJSON(response) {
  return response.json();
}

function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }

  const error = new Error(response.statusText);
  error.response = response;
  throw error;
}

function parseErrorMessage({ data }) {
  const { code, result } = data;
  if (code !== 0) {
    throw new Error('code');
  }
  return data;
}

/**
 * Requests a URL, returning a promise.
 *
 * @param  {string} url       The URL we want to request
 * @param  {object} [options] The options we want to pass to "fetch"
 * @return {object}           An object containing either "data" or "err"
 */
export default function request(url, options) {
  // 清除IE请求缓存
  let requestMode = options.method;
  let urlTime;
  const dateParameter = `&date=${new Date().getTime()}`;
  if (requestMode) {
    requestMode = requestMode.toUpperCase();
  }
  if (requestMode === 'POST') {
    urlTime = url;
    options.body += dateParameter;
  } else {
    options.method = 'GET';
    urlTime = url + dateParameter;
  }
  return fetch(urlTime, options)
        .then(checkStatus)
        .then(parseJSON)
        .then((data) => {
          return parseErrorMessage({ data });
        })
        .catch((err) => {
          throw err;
        });
}
