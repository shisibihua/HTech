/** ***************
教师类型
  @author 范丽臣
******************/
import React from 'react';
import { Button, Icon, Popconfirm } from 'antd';
import styles from './ClassroomTypeList.less';


function ClassroomTypeList({ result, EditItem, DeleteItem, addClassList }) {
  const init = (menu) => {
    return menu.map((item) => {
      return (
        <div key={item.id}>
          <span title={item.name}>{item.name}</span>
          <a onClick={() => EditItem(item.id)}><Icon type="exception" style={{ fontSize: '16px', margin: '0 10px' }} /></a>
          <Popconfirm title="是否删除?" onConfirm={() => DeleteItem(item.id)} okText="是" cancelText="否">
            <a><Icon type="delete" style={{ fontSize: '16px', margin: '0 10px' }} /></a>
          </Popconfirm>
        </div>
      );
    });
  };

  let dataList;
  if (result) {
    dataList = init(result);
  }
  return (

    <div className={styles.main}>
      {dataList}
      <div>
        <Button type="dashed" style={{ width: '200px' }} onClick={addClassList}>
          <Icon type="plus" />
        </Button>
      </div>
    </div>

  );
}

export default ClassroomTypeList;
