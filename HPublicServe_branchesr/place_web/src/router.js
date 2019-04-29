import React from 'react';
import { Router, Route, IndexRoute } from 'dva/router';
import App from './routes/App.js';
import Basics from './routes/Basics.js';
import Place from './routes/Place.js';
import ClassroomType from './routes/ClassroomType.js';

function RouterConfig({ history }) {
  return (
    <Router history={history}>
      <Route path="/" component={App}>
        <IndexRoute component={Basics} />
      </Route>
      <Route path="/place" component={App}>
        <IndexRoute component={Place} />
      </Route>
      <Route path="/classroomtype" component={App}>
        <IndexRoute component={ClassroomType} />
      </Route>
    </Router>
  );
}

export default RouterConfig;
