/** ***************
地点管理详细信息table
  @author 范丽臣
******************/
import React from 'react';
import { Table, Icon, Popconfirm } from 'antd';
import styles from './Table.less';

function TreeTableList({
  page,
  pageSize,
  onChangePage,
  total,
  PlaceListData,
  EditSchoolItem,
  loading,
  DeleteSchoolItem,
}) {
  const columns = [{
    title: '编号',
    dataIndex: 'orderNum',
    key: 'orderNum',
    width: 50,
    render(text, record, index) {
      return (pageSize * (page - 1)) + index + 1;
    },
  }, {
    title: '地点名称',
    dataIndex: 'area_name',
    key: 'area_name',
    width: 110,
  }, {
    title: '地点类型',
    dataIndex: 'type_name',
    key: 'type_name',
    width: 110,
  }, {
    title: '地点编号',
    dataIndex: 'area_code',
    key: 'area_code',
    width: 110,
  }, {
    title: '容纳人数',
    dataIndex: 'number',
    key: 'number',
    width: 110,
  }, {
    title: '是否用于选课',
    dataIndex: 'isselect',
    key: 'isselect',
    width: 110,
    render(data) {
      let text = '';
      if (data === '0') {
        text = '否';
      } else {
        text = '是';
      }
      return text;
    },
  }, {
    title: '备注',
    dataIndex: 'remark',
    key: 'remark',
    width: 110,
  }, {
    title: '操作',
    dataIndex: 'details',
    key: 'details',
    width: 110,
    render: (text, record) => (

      <div>
        {
          record.type_id === '1' || record.type_id === '2' || record.type_id === '3' || record.type_id === '4'
          ?
            <Icon type="exception" style={{ fontSize: '16px', color: '#ccc', margin: '0 10px' }} />
          :
            <a onClick={() => EditSchoolItem(record.id)}>
              <Icon type="exception" style={{ fontSize: '16px', margin: '0 10px' }} />
            </a>
        }
        {
          record.type_id === '0'
          ?
          ''
          :
          record.type_id === '1' || record.type_id === '2' || record.type_id === '3' || record.type_id === '4'
          ?
            <Icon type="delete" style={{ fontSize: '16px', color: '#ccc', margin: '0 10px' }} />
          :
            <Popconfirm title="是否删除?" onConfirm={() => DeleteSchoolItem(record.id)} okText="是" cancelText="否">
              <a><Icon type="delete" style={{ fontSize: '16px', margin: '0 10px' }} /></a>
            </Popconfirm>
        }
      </div>
    ),
  }];
  return (
    <div className={styles.tablelist}>
      <Table
        loading={loading}
        columns={columns}
        rowKey={(record, index) => index}
        dataSource={PlaceListData}
        pagination={{
          current: page,
          defaultCurrent: 1,
          pageSize,
          showQuickJumper: true,
          showTotal: () => `共${total}条`,
          total,
          onChange: onChangePage,
        }}
        bordered
      />
    </div>
  );
}

export default TreeTableList;
