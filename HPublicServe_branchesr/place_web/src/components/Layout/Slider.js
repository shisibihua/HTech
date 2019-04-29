/** ***************
  左侧组织机构树
  @author 范丽臣
******************/
import React from 'react';
import { Menu, Icon } from 'antd';
import { Link } from 'dva/router';
import './Slider.less';

const SubMenu = Menu.SubMenu;

function Slider({ TreeData, addplace, placeList }) {
/**
 * 递归循环获取左侧机构树
 * @param { Array } data 原始数据
 * @return component
 */
  const getTreeNodes = (data) => {
    return data.map((item) => {
      if (item.directories.length > 0) {
        return (
          <SubMenu
            key={item.id.toString()}
            title={
              <Link
                title={item.name}
                onClick={() => placeList(item.id)}
              >
                <Icon type="team" />
                {item.name}
                <Icon
                  type="plus-square-o"
                  onClick={() => addplace(item.id, item.name)}
                  style={{ fontSize: '16px', marginLeft: '20px' }}
                />
              </Link>
          }
          >
            {
            getTreeNodes(item.directories)
          }
          </SubMenu>
        );
      } else {
        return (
          <Menu.Item key={item.id.toString()}>
            <div>
              <Link
                title={item.name}
                onClick={() => placeList(item.id)}
              >
                {item.name}
                {
                  item.type === '49' || item.type === '50' || item.type === '39'
                  ?
                    ''
                  : <Icon
                    type="plus-square-o"
                    onClick={() => addplace(item.id, item.name)}
                    style={{ fontSize: '16px', marginLeft: '20px' }}
                  />
                }

              </Link>
            </div>
          </Menu.Item>
        );
      }
    });
  };
  // 默认打开的菜单
  let openKeys = '';
  let organization = '';
  if (TreeData.length > 0) {
    organization = getTreeNodes(TreeData);
    openKeys = TreeData[0].id.toString();
  }
  return (
    <Menu
      key={openKeys}
      style={{ minWidth: 298, border: 0 }}
      defaultOpenKeys={[openKeys]}
      mode="inline"
    >
      { organization }
    </Menu>
  );
}

export default Slider;
