import React, { PropTypes } from 'react';
import { Button, Modal, Form, Input, Radio } from 'antd';
import { connect } from 'dva';
import styles from './App.less';

const FormItem = Form.Item;
const ClassroomTypeModel = ({
  AddClassroomVisible,
  dispatch,
  resultName,
  form: {
      getFieldDecorator,
      validateFields,
      resetFields,
    },
}) => {
  let names = '';
  if (resultName == '' || resultName == undefined || resultName == null) {
    names = '';
  } else {
    names = resultName[0].name;
  }
  function CancelModel(e) {
    e.preventDefault();
    dispatch({ type: 'modal/addClassroomModalHide' });
    resetFields();
  }
  function ModelOk(e) {
    e.preventDefault();
    validateFields((err, fieldsValue) => {
      if (!err) {
        if (fieldsValue.name !== '') {
          if (resultName == '' || resultName == undefined || resultName == null) {
            dispatch({
              type: 'ClassroomTypeDate/AddClassroomTypeData',
              payload: {
                name: fieldsValue.name,
              },
            });
          } else {
            dispatch({
              type: 'ClassroomTypeDate/EditClassroomTypeData',
              payload: {
                id: resultName[0].id,
                name: fieldsValue.name,
              },
            });
          }
          resetFields();
          dispatch({ type: 'modal/addClassroomModalHide' });
          setTimeout(() => {
            dispatch({ type: 'ClassroomTypeDate/GetClassroomTypeListMain' });
          }, 500);
        }
      }
    });
  }


  return (
    <Modal
      title="名称"
      visible={AddClassroomVisible}
      onCancel={CancelModel}
      onOk={ModelOk}
      width={400}
      wrapClassName={styles.verticalCenterModal}
    >
      <div className={styles.Modelmain}>
        <FormItem label="名称：">
          {getFieldDecorator('name', {
            rules: [{ required: true, message: '请输入名称' }],
            initialValue: names,
          })(
            <Input />,
          )}
        </FormItem>
      </div>
    </Modal>
  );
};


ClassroomTypeModel.propTypes = {
  form: PropTypes.object,
  loginButtonLoading: PropTypes.bool,
};

export default Form.create()(ClassroomTypeModel);
