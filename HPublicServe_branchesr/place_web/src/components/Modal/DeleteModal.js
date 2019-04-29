/** ***************
  删除弹窗
  @author 范丽臣
******************/
import React from 'react';
import { Modal } from 'antd';
import { Link } from 'dva/router';
import styles from './Modal.less';


function DeleteModal({ dispatch, oKDelete, DeleteVisible }) {
  function CancelModal() {
    dispatch({ type: 'modal/deleteModalHide' });
  }
  return (
    <Modal
      width={260}
      title="删除"
      wrapClassName={styles.verticalCenterModal}
      visible={DeleteVisible}
      onOk={oKDelete}
      onCancel={CancelModal}
    >
      <p>确定删除？</p>
    </Modal>
  );
}

export default DeleteModal;
