import React, { PropTypes } from 'react';
import { Modal, Form, Input, Radio, Select, InputNumber, message } from 'antd';
import styles from './App.less';

const FormItem = Form.Item;
const Option = Select.Option;
const AddPlaceModal = ({
  AddPlaceVisible,
  resultData,
  dispatch,
  PlaceTypeData,
  SeeEditInfoData,
  statusSelectValue,
  statusSelect,
  SearchDataName,
  placeName,
  form: {
      getFieldDecorator,
      validateFields,
      resetFields,
    },
}) => {
  const result = SeeEditInfoData;
  let area_name = '',
    remark = '',
    isselect = '',
    type_id = '',
    type_id_class = '',
    type_idName = '',
    roomtype_id = '',
    number = '';
  if (result.length === 0) {
    area_name = '';
    type_id_class = '';
    type_id = '';
    isselect = '';
    remark = '';
    number = '';
    roomtype_id = '';
    type_idName = '';
  } else {
    area_name = result.areaname;
    isselect = result.isselect;
    remark = result.remark;
    number = result.number;
    type_id = result.type_id;
    roomtype_id = result.roomtype_id;
    type_idName = result.type_pid;
  }
  if (isselect === '') {
    isselect = 0;
  }
  if (type_id === '') {
    type_id = 6;
  }
  if (number === '') {
    number = 0;
  }
  if (roomtype_id === '' || roomtype_id === 0 || roomtype_id === null || roomtype_id === '0') {
    roomtype_id = 49;
  }

  function hideModel() {
    resetFields();
    dispatch({ type: 'modal/statusSelectHide' });
    dispatch({ type: 'modal/addPlaceModalHide' });
  }

  function ModelOk(e) {
    e.preventDefault();
    validateFields((err, fieldsValue) => {
      if (!err) {
        if (fieldsValue.area_name.replace(/^\s+|\s+$/gm, '') !== '') {
          if (SeeEditInfoData.length === 0) {
            dispatch({
              type: 'PlaceData/AddPlaceData',
              payload: {
                isselect: fieldsValue.isselect,
                name: fieldsValue.area_name,
                number: fieldsValue.number,
                remark: fieldsValue.remark,
                type_id: fieldsValue.type_id,
                roomtype_id: fieldsValue.roomtype_id,
              },
            });
          } else {
            let type_idJion;
            if (fieldsValue.type_id === '6') {
              type_idJion = fieldsValue.type_id_class;
            } else {
              type_idJion = fieldsValue.type_id;
            }
            dispatch({
              type: 'PlaceData/EditPlaceDataMain',
              payload: {
                id: SeeEditInfoData.id,
                name: fieldsValue.area_name,
                isselect: fieldsValue.isselect,
                number: fieldsValue.number,
                remark: fieldsValue.remark,
                type_id: fieldsValue.type_id,
                roomtype_id: fieldsValue.roomtype_id,
              },
            });
          }


          resetFields();
          dispatch({ type: 'modal/addPlaceModalHide' });

          dispatch({ type: 'modal/statusSelectHide' });

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
        } else {
          message.info('地点名称不能为空');
        }
      }
    });
  }
  function handleSelectChange(value) {
    if (value === '10') {
      dispatch({ type: 'modal/statusSelectShow' });
    } else {
      dispatch({ type: 'modal/statusSelectHide' });
    }
  }

  const selectPlaceData = [];
  PlaceTypeData.map((item) => {
    selectPlaceData.push(<Option value={item.id.toString()} key={item.id}>{item.name}</Option>);
  });


  const selectClassrootData = resultData.map((item) => {
    return (
      <Option value={item.id.toString()} key={item.id}>{item.name}</Option>
    );
  });
 // selectClassrootData.unshift(<Option value={'39'} key={'39'}>{'普通教室'}</Option>);
  let placeNameInfo = '';
  if (placeName === '') {
    placeNameInfo = result.p_name;
  } else {
    placeNameInfo = placeName;
  }
  return (
    <Modal
      title="地点"
      visible={AddPlaceVisible}
      onCancel={hideModel}
      onOk={ModelOk}
      width={400}
      wrapClassName={styles.verticalCenterModal}
    >
      <div className={styles.PlaceModelmain}>
        <FormItem label="当前地点：">
          <span>{placeNameInfo}</span>
        </FormItem>
        <FormItem label="地点名称：">
          {getFieldDecorator('area_name', {
            rules: [{ required: true, message: '请输入地点名称' }, { max: 16, message: '名字不能超过16个字符' }],
            initialValue: area_name,
          })(
            <Input maxLength={16} />,
          )}
        </FormItem>
        <FormItem label="地点类型：">
          {getFieldDecorator('type_id', {
            initialValue: type_id.toString(),
            onChange: handleSelectChange,
          })(
            <Select style={{ width: 260 }}>
              {selectPlaceData}
            </Select>,
           )}
        </FormItem>

        {statusSelect || type_id === '10' ?
          <FormItem label="教室类型：">
            {getFieldDecorator('roomtype_id', {
              initialValue: roomtype_id.toString(),
            })(
              <Select style={{ width: 260 }}>
                {selectClassrootData}
              </Select>,
             )}
          </FormItem> :
          ''
        }

        <FormItem label="是否用于选课：" className="collection-create-form_last-form-item">
          {getFieldDecorator('isselect', {
            initialValue: isselect.toString(),
          })(
            <Radio.Group>
              <Radio value="0" style={{ float: 'left' }}>否</Radio>
              <Radio value="1" style={{ float: 'left' }}>是</Radio>
            </Radio.Group>,
           )}
        </FormItem>
        <FormItem label="容纳人数：" >
          {getFieldDecorator('number', {
            initialValue: number,
          })(
            <InputNumber min={0} max={99999} style={{ width: 90 }} />,
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


AddPlaceModal.propTypes = {
  form: PropTypes.object,
  loginButtonLoading: PropTypes.bool,
};

export default Form.create()(AddPlaceModal);
