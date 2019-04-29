import React, { PropTypes } from 'react';
import { Button, Modal, Form, Input, Radio, Select, InputNumber } from 'antd';
import { connect } from 'dva';
import styles from './App.less';

const FormItem = Form.Item;
const Option = Select.Option;
const DetailsModal = ({
  ParVisible,
  dispatch,
  SeeStudySectiondata,
  Sectiondata,
  form: {
      getFieldDecorator,
      validateFields,
      resetFields,
    },
}) => {
  const result = SeeStudySectiondata;
  let name = '',
    number = '',
    remark = '',
    schoolSystem = '',
    status = '';
  if (result === '' || result === undefined || result === null) {
    name = '';
    number = '';
    remark = '';
    schoolSystem = '';
    status = '';
  } else {
    name = result.name;
    number = result.number;
    remark = result.remark;
    schoolSystem = result.schoolSystem;
    status = result.status;
  }
  if (status === '') {
    status = '0';
  }
  if (number === '') {
    number = '0';
  }
  if (schoolSystem === '') {
    schoolSystem = '0';
  }
  function hideModel() {
    resetFields();
    dispatch({ type: 'modal/parModalHide' });
  }

  function ModelOk(e) {
    e.preventDefault();
    validateFields((err, fieldsValue) => {
      if (!err) {
        if (fieldsValue.name.replace(/^\s+|\s+$/gm, '') !== '') {
          if (SeeStudySectiondata === '' || SeeStudySectiondata === undefined || SeeStudySectiondata === null) {
            dispatch({
              type: 'BasicsMain/AddStudySectionList',
              payload: {
                name: fieldsValue.name,
                number: fieldsValue.number,
                remark: fieldsValue.remark,
                schoolSystem: fieldsValue.schoolSystem,
                status: fieldsValue.status,
              },
            });
          } else {
            dispatch({
              type: 'BasicsMain/EditStudySectionList',
              payload: {
                id: SeeStudySectiondata.id,
                name: fieldsValue.name,
                number: fieldsValue.number,
                remark: fieldsValue.remark,
                schoolSystem: fieldsValue.schoolSystem,
                status: fieldsValue.status,
              },
            });
          }

          resetFields();

          dispatch({ type: 'modal/parModalHide' });
          setTimeout(() => {
            dispatch({
              type: 'BasicsMain/StudySectionList',
            });
          }, 500);
        }
      }
    });
  }


  return (
    <Modal
      title="学段设置"
      visible={ParVisible}
      onCancel={hideModel}
      onOk={ModelOk}
      width={400}
      wrapClassName={styles.verticalCenterModal}
    >
      <div className={styles.Modelmain}>
        <FormItem label="名称：">
          {getFieldDecorator('name', {
            rules: [{ required: true, message: '请输入学校名称' }],
            initialValue: name,
          })(
            <Input disabled />,
          )}
        </FormItem>
        <FormItem label="状态：" className="collection-create-form_last-form-item">
          {getFieldDecorator('status', {
            initialValue: status,
          })(
            <Radio.Group>
              <Radio value="1" style={{ float: 'left' }}>启用</Radio>
              <Radio value="0" style={{ float: 'left' }}>不启用</Radio>
            </Radio.Group>,
           )}
        </FormItem>
        <FormItem label="学年：">
          {getFieldDecorator('schoolSystem', {
            initialValue: schoolSystem,
            rules: [{
              pattern: /^[0-9]\d*$/,
              message: '请输入正确学年',
            }],
          })(
            <InputNumber style={{ width: 60 }} />,
            )}
        </FormItem>
        <FormItem label="排序：">
          {getFieldDecorator('number', {
            initialValue: number,
            rules: [{
              pattern: /^[0-9]\d*$/,
              message: '请输入正确序号',
            }],
          })(
            <InputNumber />,
          )}
        </FormItem>

        <FormItem label="备注：">
          {getFieldDecorator('remark', {
            initialValue: remark,
          })(
            <Input type="textarea" />,
          )}
        </FormItem>
      </div>
    </Modal>
  );
};


DetailsModal.propTypes = {
  form: PropTypes.object,
  loginButtonLoading: PropTypes.bool,
};

export default Form.create()(DetailsModal);
