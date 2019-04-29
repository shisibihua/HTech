import { parse } from 'qs';
import { message } from 'antd';
import { getPlaceTree, getPlaceTableMain, getPlaceType, AddPlaceData, DeletePlaceData, SeeEditPlaceData, EditPlaceDataMain, SeachPlaceDataMain } from '../services/Place';

export default {
  namespace: 'PlaceData',
  state: {
    TreeData: [],
    PlaceListData: [],
    PlaceTypeData: [],
    SeeEditInfoData: [],
    statusSelectValue: false,
    // 当前选择的地点id
    currentAreaId: '',
    page: 1,
    pageSize: 10,
    total: 0,
    // 搜索关键词
    searchWord: '',
    // 要修改的地点id
    editAreaId: '',
  },
  reducers: {
    changeState(state, { payload }) {
      return {
        ...state,
        ...payload,
      };
    },
    PlaceListSuccess(state, action) {
      return {
        ...state,
        PlaceListData: action.payload.PlaceListData,
      };
    },
    PlaceTypeSuccess(state, action) {
      return {
        ...state,
        PlaceTypeData: action.payload.PlaceTypeData,
      };
    },
    SeeEditPlaceDataSuccess(state, action) {
      return {
        ...state,
        SeeEditInfoData: action.payload.SeeEditInfoData,
      };
    },
    statusSelectShow(state) {
      return {
        ...state,
        statusSelectValue: true,
      };
    },


    statusSelectHide(state) {
      return {
        ...state,
        statusSelectValue: false,
      };
    },
  },
  effects: {
    // 获取组织机构树数据
    *getPlaceTree({ payload }, { call, put, select }) {
      const {
        userId, // 用户id
      } = yield select(({ app }) => app);
      const params = {
        userId,
      };
      const data = yield call(getPlaceTree, params);
      let result = [];
      if (data.code === 0 && data.result) {
        result = [data.result];
      }
      yield put({
        type: 'changeState',
        payload: {
          TreeData: result,
        },
      });
    },

    // 获取地点列表
    *getPlaceTableMain({ payload }, { call, put, select }) {
      const {
        currentAreaId,
        page,
        pageSize,
        searchWord,
      } = yield select(({ PlaceData }) => PlaceData);
      const params = {
        areaId: currentAreaId,
        page,
        pageSize,
        searchWord,
      };
      const data = yield call(getPlaceTableMain, params);

      let result = [];
      let total = 0;
      if (data.code === 0 && data.result && data.result.data) {
        result = data.result.data;
        total = data.result.total;
      }
      yield put({
        type: 'changeState',
        payload: {
          PlaceListData: result,
          total,
        },
      });
    },


    // 获取地点类型
    *getPlaceType({ payload }, { call, put }) {
      const data = yield call(getPlaceType);
      if (data.code === 0 || data.result.length > 0) {
        yield put({
          type: 'PlaceTypeSuccess',
          payload: {
            PlaceTypeData: data.result,
          },
        });
      }
    },


    // 添加地点

    *AddPlaceData({ payload }, { call, put, select }) {
      const { p_id } = yield select(({ modal }) => modal);
      const params = {
        isselect: payload.isselect,
        name: payload.name,
        number: payload.number,
        p_id,
        remark: payload.remark,
        type_id: payload.type_id,
        roomtype_id: payload.roomtype_id,
      };
      const data = yield call(AddPlaceData, parse(params));
      if (data.result === true) {
        message.success('添加成功');
      } else {
        message.error('添加失败');
      }
    },


    // 删除地点
    *DeletePlaceData({ payload }, { call, put }) {
      const params = {
        id: payload.id,
      };
      const data = yield call(DeletePlaceData, parse(params));
      if (data.result === '1') {
        message.success('删除成功');
      } else if (data.result === '2') {
        message.error('删除失败');
      } else if (data.result === '3') {
        message.error('该地点下存在子地点，请先删除子地点');
      } else if (data.result === '4') {
        message.error('该地点下挂有设备，请先解绑设备');
      } else if (data.result === '5') {
        message.error('该地点与班级存在联系，请先解除');
      } else {
        message.error('删除失败');
      }
    },


    // 获取要修改地点信息
    *SeeEditPlaceData({ payload }, { call, put, select }) {
      if (payload.id === '') {
        yield put({
          type: 'SeeEditPlaceDataSuccess',
          payload: {
            SeeEditInfoData: [],
          },
        });
      } else {
        const params = {
          id: payload.id,
        };
        const data = yield call(SeeEditPlaceData, parse(params));
        if (data.result[0]) {
          if (data.result[0].type_id === 6 || data.result[0].type_id === '6') {
            yield put({ type: 'modal/statusSelectShow' });
          } else {
            yield put({ type: 'modal/statusSelectHide' });
          }
        }
        if (data.code === 0 || data.result.length > 0) {
          yield put({
            type: 'SeeEditPlaceDataSuccess',
            payload: {
              SeeEditInfoData: data.result[0],
            },
          });
        }
      }
    },


    // 修改地点信息
    *EditPlaceDataMain({ payload }, { call, select }) {
      const { p_id } = yield select(({ modal }) => modal);
      const { editAreaId } = yield select(({ PlaceData }) => PlaceData);
      const params = {
        id: editAreaId,
        name: payload.name,
        isselect: payload.isselect,
        number: payload.number,
        p_id,
        remark: payload.remark,
        type_id: payload.type_id,
        roomtype_id: payload.roomtype_id === undefined ? '' : payload.roomtype_id,
      };
      const data = yield call(EditPlaceDataMain, parse(params));
      if (data.result === true) {
        message.success('修改成功');
      } else {
        message.error('修改失败');
      }
    },


    // 搜索
    *SeachPlaceDataMain({ payload }, { call, put, select }) {
      const {
        currentAreaId,
        page,
        pageSize,
      } = yield select(({ PlaceData }) => PlaceData);
      const params = {
        name: payload.name,
        areaId: currentAreaId,
        page,
        pageSize,
      };
      const data = yield call(SeachPlaceDataMain, parse(params));
      console.log('data===', data);
      let result = [];
      let total = 0;
      if (data.code === 0 && data.result && data.result.data) {
        result = data.result.data;
        total = data.result.total;
      }
      yield put({
        type: 'changeState',
        payload: {
          PlaceListData: result,
          total,
        },
      });
    },

  },
  subscriptions: {
    setup({ dispatch, history }) {
      history.listen((location) => {
        if (location.pathname === '/place') {
          dispatch({
            type: 'getPlaceTree',
          });
          dispatch({
            type: 'getPlaceType',
          });
        }
      });
    },
  },
};
