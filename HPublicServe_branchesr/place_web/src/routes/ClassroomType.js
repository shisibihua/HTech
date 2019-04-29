import React from 'react';
import { connect } from 'dva';
import { Tabs } from 'antd';
import ClassroomTypeList from '../components/Layout/ClassroomTypeList.js';
import ClassroomTypeModel from './ClassroomTypeModel.js';
import styles from './App.less';
import DeleteModalMouid from '../components/Modal/DeleteModal.js';

const TabPane = Tabs.TabPane;

function ClassRoomType({ location, dispatch, ClassroomTypeDate, modal }) {
  const { AddClassroomVisible, DeleteVisible, DeleteId, type } = modal;
  const { result, resultName } = ClassroomTypeDate;


  const classModelJion = {
    AddClassroomVisible,
    dispatch,
    resultName,
  };

  const DeleteModalJon = {
    DeleteVisible,
    dispatch,
    oKDelete() {
      dispatch({ type: 'modal/deleteModalHide' });
      if (type === 'classroom') {
        dispatch({
          type: 'ClassroomTypeDate/DeleteClassroomTypeData',
          payload: {
            id: DeleteId,
          },
        });
        setTimeout(() => {
          dispatch({ type: 'ClassroomTypeDate/GetClassroomTypeListMain' });
        }, 500);
      }
    },
  };
  const classListJion = {
    EditItem(Item) {
      dispatch({
        type: 'ClassroomTypeDate/SeeClassroomTypeData',
        payload: {
          id: Item,
        },
      });
      dispatch({ type: 'modal/addClassroomModalShow' });
    },

    DeleteItem(Item) {
      dispatch({
        type: 'ClassroomTypeDate/DeleteClassroomTypeData',
        payload: {
          id: Item,
        },
      });
      setTimeout(() => {
        dispatch({ type: 'ClassroomTypeDate/GetClassroomTypeListMain' });
      }, 500);
    },
    result,
    addClassList() {
      dispatch({
        type: 'ClassroomTypeDate/SeeClassroomTypeData',
        payload: {
          id: '',
        },
      });
      dispatch({ type: 'modal/addClassroomModalShow' });
    },
  };
  return (
    <div className={styles.content}>
      <ClassroomTypeList {...classListJion} />
      <ClassroomTypeModel {...classModelJion} />
      <DeleteModalMouid {...DeleteModalJon} />
    </div>
  );
}

function mapStateToProps({ ClassroomTypeDate, modal }) {
  return { ClassroomTypeDate, modal };
}

export default connect(mapStateToProps)(ClassRoomType);
