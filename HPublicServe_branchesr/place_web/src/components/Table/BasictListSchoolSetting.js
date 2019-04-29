/** ***************
基础信息校区table
  @author 范丽臣
******************/
import React from 'react';
import { connect } from 'dva';
import { Table, Icon, Popconfirm } from 'antd';
import styles from './Table.less';

function BasictListSchoolSetting({ Schooldata, EditSchoolItem, loading, DeleteSchoolItem }) {
  const columns = [{
    title: '编号',
    dataIndex: 'orderNum',
    key: 'orderNum',
    width: 50,
    render(text, record, index) {
      return index + 1;
    },
  }, {
    title: '名称',
    dataIndex: 'name',
    key: 'name',
    width: 130,
  }, {
    title: '地址',
    dataIndex: 'location',
    key: 'location',
    width: 130,
  }, {
    title: '电话',
    dataIndex: 'telephone',
    key: 'telephone',
    width: 130,
  }, {
    title: '传真',
    dataIndex: 'fax',
    key: 'fax',
    width: 130,
  }, {
    title: '邮编',
    dataIndex: 'postcode',
    key: 'postcode',
    width: 130,
  }, {
    title: '操作',
    dataIndex: 'details',
    key: 'details',
    width: 130,
    render: (text, record) => (
      <div>
        <a onClick={() => EditSchoolItem(record.id)}><Icon type="exception" style={{ fontSize: '16px', margin: '0 10px' }} /></a>
        <Popconfirm title="是否删除?" onConfirm={() => DeleteSchoolItem(record.id)} okText="是" cancelText="否">
          <a><Icon type="delete" style={{ fontSize: '16px', margin: '0 10px' }} /></a>
        </Popconfirm>
      </div>
    ),
  }];

  return (
    <div className={styles.tablelist}>
      <Table loading={loading} columns={columns} rowKey={record => record.id} dataSource={Schooldata} pagination={false} bordered />
    </div>
  );
}

function mapStateToProps(state) {
  return {
    // loading: state.loading.models.BasicsMain,
  };
}

export default connect(mapStateToProps)(BasictListSchoolSetting);
