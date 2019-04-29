/** ***************
基础信息学段table
  @author 范丽臣
******************/
import React from 'react';
import { connect } from 'dva';
import styles from './Table.less';
import { Table, Icon } from 'antd';

function BasictListParagraphSetting({ StudySectiondata, loading, EditSectionItem, DeleteSectionItem }) {
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
    width: 100,
  }, {
    title: '学制（年）',
    dataIndex: 'schoolSystem',
    key: 'schoolSystem',
    width: 80,
  }, {
    title: '状态',
    dataIndex: 'status',
    key: 'status',
    width: 80,
    render(data) {
      let text = '';
      if (data == 0) {
        text = '不启用';
      } else {
        text = '启用';
      }
      return text;
    },
  }, {
    title: '排序',
    dataIndex: 'number',
    key: 'number',
    width: 80,
  }, {
    title: '备注',
    dataIndex: 'remark',
    key: 'remark',
    width: 200,
  }, {
    title: '操作',
    dataIndex: 'details',
    key: 'details',
    width: 80,
    render: (text, record) => (
      <div>
        <a onClick={() => EditSectionItem(record.id)}><Icon type="exception" style={{ fontSize: '16px', margin: '0 10px' }} /></a>
        <a onClick={() => DeleteSectionItem(record.id)}><Icon type="delete" style={{ fontSize: '16px', margin: '0 10px', display: 'none' }} /></a>
      </div>
    ),
  }];

  return (
    <div className={styles.tablelist}>
      <Table loading={loading} columns={columns} rowKey={record => record.id} dataSource={StudySectiondata} pagination={false} bordered />
    </div>
  );
}

function mapStateToProps(state) {
  return {
    // loading: state.loading.models.BasicsMain,
  };
}

export default connect(mapStateToProps)(BasictListParagraphSetting);
