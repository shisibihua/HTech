import { parse } from 'qs';
import { message } from 'antd';

export default {
  namespace: 'modal',
  state: {
    ModifyVisible: false,
    SchoolVisible: false,
    ParVisible: false,
    AddClassroomVisible: false,
    PlaceVisible: false,
    AddPlaceVisible: false,
    statusSelect: false,
    DeleteVisible: false,
    p_id: '',
    placeName: '',
    SearchDataName: '',
    DeleteId: '',
    type: '',
  },
  reducers: {
    searchDataMain(state, action) {
      return {
        ...state,
        SearchDataName: action.payload.SearchDataName,
      };
    },

    placeNameMainSuccess(state, action) {
      return {
        ...state,
        placeName: action.payload.placeName,
      };
    },


    statusSelectShow(state) {
      return {
        ...state,
        statusSelect: true,
      };
    },


    statusSelectHide(state) {
      return {
        ...state,
        statusSelect: false,
      };
    },


    modifyModalShow(state) {
      return {
        ...state,
        ModifyVisible: true,
      };
    },
    modifyModalHide(state) {
      return {
        ...state,
        ModifyVisible: false,
      };
    },


    schoolModalShow(state) {
      return {
        ...state,
        SchoolVisible: true,
      };
    },
    schoolModalHide(state) {
      return {
        ...state,
        SchoolVisible: false,
      };
    },


    parModalShow(state) {
      return {
        ...state,
        ParVisible: true,
      };
    },
    parModalHide(state) {
      return {
        ...state,
        ParVisible: false,
      };
    },


    addClassroomModalShow(state) {
      return {
        ...state,
        AddClassroomVisible: true,
      };
    },
    addClassroomModalHide(state) {
      return {
        ...state,
        AddClassroomVisible: false,
      };
    },


    placeModalShow(state) {
      return {
        ...state,
        PlaceVisible: true,
      };
    },
    placeModalHide(state) {
      return {
        ...state,
        PlaceVisible: false,
      };
    },


    deleteModalShow(state) {
      return {
        ...state,
        DeleteVisible: true,
      };
    },

    deleteModalHide(state) {
      return {
        ...state,
        DeleteVisible: false,
      };
    },

    deleteIdMain(state, action) {
      return {
        ...state,
        DeleteId: action.payload.DeleteId,
        type: action.payload.type,
      };
    },

    addPlaceModalShow(state) {
      return {
        ...state,
        AddPlaceVisible: true,
      };
    },
    addPlaceModalHide(state) {
      return {
        ...state,
        AddPlaceVisible: false,
      };
    },

    addPlaceIdSuccess(state, action) {
      return {
        ...state,
        p_id: action.payload.p_id,
      };
    },


  },
  effects: {
    *addPlaceId({ payload }, { call, put }) {
      yield put({
        type: 'addPlaceIdSuccess',
        payload: {
          p_id: payload.p_id,
        },
      });
    },

    *placeNameMain({ payload }, { call, put }) {
      yield put({
        type: 'placeNameMainSuccess',
        payload: {
          placeName: payload.placeName,
        },
      });
    },
    *searchDataNameData({ payload }, { call, put }) {
      yield put({
        type: 'searchDataMain',
        payload: {
          SearchDataName: payload.name,
        },
      });
    },


    *getDeleteId({ payload }, { call, put }) {
      yield put({
        type: 'deleteIdMain',
        payload: {
          DeleteId: payload.DeleteId,
          type: payload.type,
        },
      });
    },
  },
  subscriptions: {

  },
};
