import React, { PropTypes } from 'react';
import { Modal, Form, Input, message } from 'antd';
import styles from './App.less';

const FormItem = Form.Item;
const DetailsModal = ({
  SchoolVisible,
  dispatch,
  SeeSchooldata,
  form: {
      getFieldDecorator,
      validateFields,
      resetFields,
    },
}) => {
  const result = SeeSchooldata;
  let name = '',
    location = '',
    telephone = '',
    postcode = '',
    fax = '';
  if (result === '' || result === undefined || result === null) {
    name = '';
    location = '';
    telephone = '';
    postcode = '';
    fax = '';
  } else {
    name = result.name;
    location = result.location;
    telephone = result.telephone;
    postcode = result.postcode;
    fax = result.fax;
  }
  function hideModel() {
    resetFields();
    dispatch({ type: 'modal/schoolModalHide' });
  }

  function ModelOk(e) {
    e.preventDefault();
    validateFields((err, fieldsValue) => {
      if (!err) {
        if (fieldsValue.name.replace(/^\s+|\s+$/gm, '') !== '') {
          if (SeeSchooldata === '' || SeeSchooldata === undefined || SeeSchooldata === null) {
            dispatch({
              type: 'BasicsMain/AddBasicsSchool',
              payload: {
                fax: fieldsValue.fax,
                location: fieldsValue.location,
                name: fieldsValue.name,
                telephone: fieldsValue.telephone,
                postcode: fieldsValue.postcode,
              },
            });
          } else {
            dispatch({
              type: 'BasicsMain/EditBasicsSchool',
              payload: {
                id: SeeSchooldata.id,
                fax: fieldsValue.fax,
                location: fieldsValue.location,
                name: fieldsValue.name,
                telephone: fieldsValue.telephone,
                postcode: fieldsValue.postcode,
              },
            });
          }

          resetFields();

          dispatch({ type: 'modal/schoolModalHide' });

          setTimeout(() => {
            dispatch({
              type: 'BasicsMain/GetBasicsSchoolList',
            });
          }, 500);
        } else {
          message.info('名称不能为空');
        }
      }
    });
  }


  return (
    <Modal
      title="校区设置"
      visible={SchoolVisible}
      onCancel={hideModel}
      onOk={ModelOk}
      width={400}
      wrapClassName={styles.verticalCenterModal}
    >
      <div className={styles.Modelmain}>
        <FormItem label="名称：">
          {getFieldDecorator('name', {
            rules: [{ required: true, message: '请输入分校名称' }],
            initialValue: name,
          })(
            <Input />,
          )}
        </FormItem>
        <FormItem label="地址：">
          {getFieldDecorator('location', {
            initialValue: location,
          })(
            <Input />,
          )}
        </FormItem>
        <FormItem label="电话：">
          {getFieldDecorator('telephone', {
            rules: [{
              pattern: /^((0\d{2,3}-\d{7,8})|(1[3|4|5|7|8][0-9]{9}))$/,
              message: '请输入正确电话号码',
            }],
            initialValue: telephone,
          })(
            <Input />,
          )}
        </FormItem>
        <FormItem label="传真：">
          {getFieldDecorator('fax', {
            rules: [{
              pattern: /^((0\d{2,3}-)?\d{7,8})$/,
              message: '请输入正确传真',
            }],
            initialValue: fax,
          })(
            <Input />,
          )}
        </FormItem>
        <FormItem label="邮编：">
          {getFieldDecorator('postcode', {
            rules: [{
              pattern: /\d{6}/,
              message: '请输入正确邮编',
            }],
            initialValue: postcode,
          })(
            <Input />,
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
