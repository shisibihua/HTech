import React from 'react';
import { connect } from 'dva';
import { Input } from 'antd';
import styles from './App.less';
import Slider from '../components/Layout/Slider.js';
import TreeTableList from '../components/Table/TreeTableList.js';
import AddPlaceModal from './AddPlaceModal.js';
import DeleteModalMouid from '../components/Modal/DeleteModal.js';

const SearchInput = Input.Search;
function Place({ dispatch, PlaceData, modal, ClassroomTypeDate }) {
  const {
    TreeData,
    PlaceListData,
    PlaceTypeData,
    SeeEditInfoData,
    statusSelectValue,
    // 当前地点id
    currentAreaId,
    // 当前页
    page,
    // 每页数据量
    pageSize,
    // 数据总条数
    total,
    // 搜索关键词
    searchWord,
  } = PlaceData;

  const { AddPlaceVisible, statusSelect, SearchDataName, DeleteVisible, DeleteId, type, placeName } = modal;
  const { result } = ClassroomTypeDate;
  const resultData = result;

  // 组织树需要参数
  const TreeJion = {
    TreeData,
    addplace(itme, cname) {
      dispatch({ type: 'modal/addPlaceModalShow' });
      dispatch({
        type: 'modal/addPlaceId',
        payload: {
          p_id: itme,
        },
      });
      dispatch({
        type: 'modal/placeNameMain',
        payload: {
          placeName: cname,
        },
      });
      dispatch({
        type: 'PlaceData/SeeEditPlaceData',
        payload: {
          id: '',
        },
      });
      dispatch({ type: 'modal/statusSelectHide' });
      dispatch({ type: 'PlaceData/statusSelectHide' });
    },
    placeListHead() {
      dispatch({
        type: 'PlaceData/getPlaceTableMain',
        payload: {
          p_id: '',
        },
      });
      dispatch({
        type: 'modal/addPlaceId',
        payload: {
          p_id: '',
        },
      });
    },
    placeList(areaId) {
      dispatch({
        type: 'PlaceData/changeState',
        payload: {
          currentAreaId: areaId,
          page: 1,
          searchWord: '',
        },
      });
      // 获取该地点下地点列表
      dispatch({
        type: 'PlaceData/getPlaceTableMain',
      });

      dispatch({
        type: 'modal/addPlaceId',
        payload: {
          p_id: areaId,
        },
      });
      dispatch({
        type: 'modal/searchDataNameData',
        payload: {
          name: '',
        },
      });
    },
  };
  const AddPlaceModaJion = {
    SearchDataName,
    resultData,
    PlaceTypeData,
    statusSelectValue,
    dispatch,
    AddPlaceVisible,
    statusSelect,
    SeeEditInfoData,
    placeName,
  };
  const DeleteModalJon = {
    DeleteVisible,
    dispatch,
    oKDelete() {
      dispatch({ type: 'modal/deleteModalHide' });
      if (type === 'place') {
        dispatch({
          type: 'PlaceData/DeletePlaceData',
          payload: {
            id: DeleteId,
          },
        });
        setTimeout(() => {
          if (SearchDataName) {
            dispatch({
              type: 'PlaceData/SeachPlaceDataMain',
              payload: {
                name: SearchDataName,
              },
            });
          } else {
            dispatch({
              type: 'PlaceData/getPlaceTableMain',
            });
          }
          dispatch({
            type: 'PlaceData/getPlaceTree',
          });
        }, 500);
      }
    },
  };
  const PlaceTableJion = {
    // 当前页
    page,
    // 每页数据量
    pageSize,
    // 数据总条数
    total,
    // 分页 改变页码
    /**
     * 分页 改变页码
     * @param { Number } pageNum 页码
     */
    onChangePage(pageNum) {
      dispatch({
        type: 'PlaceData/changeState',
        payload: {
          page: pageNum,
        },
      });
      // 加载数据
      dispatch({ type: 'PlaceData/getPlaceTableMain' });
    },
    DeleteSchoolItem(itme) {
      dispatch({
        type: 'PlaceData/DeletePlaceData',
        payload: {
          id: itme,
        },
      });
      setTimeout(() => {
        if (SearchDataName) {
          dispatch({
            type: 'PlaceData/SeachPlaceDataMain',
            payload: {
              name: SearchDataName,
            },
          });
        } else {
          dispatch({
            type: 'PlaceData/getPlaceTableMain',
          });
        }
        dispatch({
          type: 'PlaceData/getPlaceTree',
        });
      }, 500);
      // dispatch({
      //   type: 'modal/deleteModalShow',
      // });
      // dispatch({
      //   type: 'modal/deleteIdMain',
      //   payload: {
      //     DeleteId: itme,
      //     type: 'place',
      //   },
      // });
    },
    EditSchoolItem(itme) {
      dispatch({
        type: 'PlaceData/changeState',
        payload: {
          editAreaId: itme,
        },
      });
      dispatch({ type: 'modal/addPlaceModalShow' });
      dispatch({
        type: 'PlaceData/SeeEditPlaceData',
        payload: {
          id: itme,
        },
      });
      dispatch({
        type: 'modal/placeNameMain',
        payload: {
          placeName: '',
        },
      });
    },
    PlaceListData,
  };
  // 搜索
  function SearchDataByText(keyword) {
    dispatch({
      type: 'PlaceData/changeState',
      payload: {
        searchWord: keyword,
      },
    });
    dispatch({
      type: 'PlaceData/getPlaceTableMain',
    });


    dispatch({
      type: 'modal/searchDataNameData',
      payload: {
        name: keyword,
      },
    });
  }
  return (
    <div className={styles.content} style={{ width: '1232px' }}>
      <div className={styles.sliderBer}>
        <div className={styles.slider}>
          <div className={styles.top}>
          地点组织
          </div>
          <Slider {...TreeJion} />
        </div>
      </div>
      <div className={styles.info}>
        <div className={styles.top}>
          详细信息
        </div>
        <div className={styles.Search}>
          <SearchInput
            key={currentAreaId + searchWord}
            placeholder="请输入地点名称"
            style={{ width: 180, float: 'left', marginLeft: 8 }}
            size="large"
            defaultValue={searchWord}
            onSearch={SearchDataByText}
          />
          <AddPlaceModal {...AddPlaceModaJion} />

        </div>
        <div className={styles.table}>
          <TreeTableList {...PlaceTableJion} />
        </div>
      </div>
      <DeleteModalMouid {...DeleteModalJon} />
    </div>
  );
}

function mapStateToProps({ PlaceData, modal, ClassroomTypeDate }) {
  return { PlaceData, modal, ClassroomTypeDate };
}

export default connect(mapStateToProps)(Place);
