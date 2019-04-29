import React from 'react';
import { connect } from 'dva';
import styles from './App.less';
import BasictList from '../components/Layout/BasictList.js';
import DeleteModalMouid from '../components/Modal/DeleteModal.js';
import ModalBasictModifySchool from './BasictModifySchool.js';
import ModalBasictSchoolSetting from './BasictSchoolSetting.js';
import ModalBasictParSetting from './BasictParSetting.js';


function Basics({ location, dispatch, modal, BasicsMain }) {
  // result : 学校基本信息数据
  // Schooldata：  分校数据
  // SeeSchooldata：  查找修改分校信息
  const { result, Schooldata, SeeSchooldata, StudySectiondata, SeeStudySectiondata, GetLogoInfoData } = BasicsMain;
  const { ModifyVisible, SchoolVisible, ParVisible, DeleteVisible, DeleteId, type } = modal;
  const ModalJoin = {
    SchoolVisible,
    ParVisible,
    ModifyVisible,
    dispatch,
    result,
    SeeSchooldata,
    StudySectiondata,
    SeeStudySectiondata,
  };
  const DeleteModalJon = {
    DeleteVisible,
    dispatch,
    oKDelete() {
      dispatch({ type: 'modal/deleteModalHide' });
      if (type === 'StudySection') {
        dispatch({
          type: 'BasicsMain/DeleteStudySectionList',
          payload: {
            id: DeleteId,
          },
        });
        setTimeout(() => {
          dispatch({
            type: 'BasicsMain/StudySectionList',
          });
        }, 500);
      }
    },
  };
  function modifyModal() {
    dispatch({ type: 'modal/modifyModalShow' });
  }
  function SchoolModal() {
    dispatch({ type: 'modal/schoolModalShow' });
    dispatch({
      type: 'BasicsMain/SeeEditBasicsSchool',
      payload: {
        id: '',
      },
    });
  }
  function ParModal() {
    dispatch({ type: 'modal/parModalShow' });
    dispatch({
      type: 'BasicsMain/SeeStudySectionList',
      payload: {
        id: '',
      },
    });
  }
  const ListJoin = {
    GetLogoInfoData,
    handleChange(info) {
      if (info.file.response) {
        dispatch({
          type: 'BasicsMain/UploadLogo',
          payload: {
            img_name: info.file.response,
          },
        });
      }
    },
    modifyModal,
    result,
    SchoolModal,
    ParModal,
    Schooldata,
    SeeSchooldata,
    StudySectiondata,
    EditSectionItem(Item) {
      dispatch({ type: 'modal/parModalShow' });
      dispatch({
        type: 'BasicsMain/SeeStudySectionList',
        payload: {
          id: Item,
        },
      });
    },
    DeleteSectionItem(Item) {
      dispatch({
        type: 'modal/deleteModalShow',
      });
      dispatch({
        type: 'modal/deleteIdMain',
        payload: {
          DeleteId: Item,
          type: 'StudySection',
        },
      });
    },
    EditSchoolItem(Item) {
      dispatch({ type: 'modal/schoolModalShow' });
      dispatch({
        type: 'BasicsMain/SeeEditBasicsSchool',
        payload: {
          id: Item,
        },
      });
    },
    DeleteSchoolItem(Item) {
      dispatch({
        type: 'BasicsMain/DeleteBasicsSchool',
        payload: {
          id: Item,
        },
      });
      setTimeout(() => {
        dispatch({
          type: 'BasicsMain/GetBasicsSchoolList',
        });
      }, 500);
    },
  };
  return (
    <div className={styles.content}>
      <BasictList {...ListJoin} />
      <ModalBasictModifySchool {...ModalJoin} />
      <ModalBasictSchoolSetting {...ModalJoin} />
      <ModalBasictParSetting {...ModalJoin} />
      <DeleteModalMouid {...DeleteModalJon} />
    </div>
  );
}

function mapStateToProps({ modal, BasicsMain }) {
  return { modal, BasicsMain };
}

export default connect(mapStateToProps)(Basics);
