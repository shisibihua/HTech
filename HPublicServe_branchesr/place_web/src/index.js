import 'babel-polyfill';
import dva from 'dva';
import './index.html';
import './index.css';
import createLoading from 'dva-loading';

// 1. Initialize
const app = dva({
  onError(e) {
    console.log('后台服务未启动', e);
  },
});


// 2. Plugins
app.use(createLoading());

// 3. Model
app.model(require('./models/App'));
app.model(require('./models/Modal'));
app.model(require('./models/Basics'));
app.model(require('./models/ClassroomType'));
app.model(require('./models/Place'));


// 4. Router
app.router(require('./router'));

// 5. Start
app.start('#root');
