/** ***************
header切换
  @author 范丽臣
******************/
import { routerRedux } from 'dva/router';

export default {
  namespace: 'app',
  state: {
    currentNavigation: 'basics', // header切换参数
    userId: '',
    userRealName: '',
    logOut: '', // 退出地址
  },
  reducers: {
    changeNavigation(state, action) {
      return {
        ...state,
        currentNavigation: action.payload.currentNavigation,
      };
    },
    updateUserInfo(state, action) {
      return {
        ...state,
        ...action.payload,
      };
    },
  },
  effects: {

  },
  subscriptions: {
    setup({ dispatch, history }) {
      history.listen((location) => {
        const userId = location.query.userId;
        const userRealName = location.query.userRealName;
        const logOut = location.query.logOut;
        if (userId) {
          dispatch({
            type: 'updateUserInfo',
            payload: {
              userId,
              userRealName,
              logOut,
            },
          });
        }
        if (location.pathname === '/') {
          dispatch({
            type: 'changeNavigation',
            payload: {
              currentNavigation: 'basics',
            },
          });
          dispatch(routerRedux.push({
            pathname: '/place',
            query: {
              userId,
              userRealName,
              logOut,
            },
          }));
        } else if (location.pathname === '/place') {
          dispatch({
            type: 'changeNavigation',
            payload: {
              currentNavigation: 'place',
            },
          });
        } else if (location.pathname === '/classroomtype') {
          dispatch({
            type: 'changeNavigation',
            payload: {
              currentNavigation: 'classroomtype',
            },
          });
        }
      });
    },
  },
};
