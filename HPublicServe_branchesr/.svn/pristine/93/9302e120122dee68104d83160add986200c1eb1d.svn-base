import React, { PropTypes } from 'react';
import { Modal, Form, Input, message } from 'antd';
import styles from './App.less';

const FormItem = Form.Item;
const DetailsModal = ({
  ModifyVisible,
  dispatch,
  result,
  form: {
      getFieldDecorator,
      validateFields,
      resetFields,
    },
}) => {
  function hideModel() {
    resetFields();
    dispatch({ type: 'modal/modifyModalHide' });
  }

  function ModelOk(e) {
    e.preventDefault();
    validateFields((err, fieldsValue) => {
      if (!err) {
        if (fieldsValue.cn_name.replace(/^\s+|\s+$/gm, '') !== '') {
          dispatch({
            type: 'BasicsMain/TodoBasicsInfo',
            payload: {
              cn_name: fieldsValue.cn_name,
              en_name: fieldsValue.en_name,
              location: fieldsValue.location,
              mail: fieldsValue.mail,
              postcode: fieldsValue.postcode,
              region: fieldsValue.region,
              website: fieldsValue.website,
              phone: fieldsValue.phone,
              fax: fieldsValue.fax,
            },
          });

          resetFields();

          dispatch({ type: 'modal/modifyModalHide' });
          setTimeout(() => {
            dispatch({
              type: 'BasicsMain/GetBasicsInfo',
            });
          }, 500);
        } else {
          message.info('学校名称不能为空');
        }
      }
    });
  }


  let cn_name = '',
    en_name = '',
    region = '',
    location = '',
    phone = '',
    postcode = '',
    website = '',
    mail = '',
    fax = '';
  if (result.length === 0) {
    cn_name = '';
    en_name = '';
    region = '';
    location = '';
    phone = '';
    postcode = '';
    website = '';
    mail = '';
    fax = '';
  } else {
    cn_name = result[0].cn_name;
    en_name = result[0].en_name;
    region = result[0].region;
    location = result[0].location;
    phone = result[0].phone;
    postcode = result[0].postcode;
    website = result[0].website;
    mail = result[0].mail;
    fax = result[0].fax;
  }


  return (
    <Modal
      title="基本信息"
      visible={ModifyVisible}
      onCancel={hideModel}
      onOk={ModelOk}
      width={400}
      wrapClassName={styles.verticalCenterModal}
    >
      <div className={styles.Modelmain}>
        <FormItem label="学校名称：">
          {getFieldDecorator('cn_name', {
            rules: [{ required: true, message: '请输入学校名称' }],
            initialValue: cn_name,
          })(
            <Input />,
          )}
        </FormItem>
        <FormItem label="英文名称：">
          {getFieldDecorator('en_name', {
            initialValue: en_name,
            rules: [{
              pattern: /^[^\u4e00-\u9fa5]{0,}$/,
              message: '请输入正确英文名称',
            }],
          })(
            <Input />,
          )}
        </FormItem>
        <FormItem label="地区：">
          {getFieldDecorator('region', {
            initialValue: region,
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
          {getFieldDecorator('phone', {
            rules: [{
              pattern: /^((0\d{2,3}-\d{7,8})|(1[3|4|5|7|8][0-9]{9}))$/,
              message: '请输入正确电话号码',
            }],
            initialValue: phone,
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
        <FormItem label="邮箱：">
          {getFieldDecorator('mail', {
            rules: [{
              pattern: /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/,
              message: '请输入正确邮箱',
            }],
            initialValue: mail,
          })(
            <Input />,
          )}
        </FormItem>
        <FormItem label="网址：">
          {getFieldDecorator('website', {
            // rules: [{
            //   pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/,
            //   message: '请输入电话号码',
            // }],
            initialValue: website,
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
