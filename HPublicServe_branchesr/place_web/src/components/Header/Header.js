/** ***************
  header导航栏组件
  @author 范丽臣
******************/
import React from 'react';
import { connect } from 'dva';
import { Menu, Dropdown, Icon } from 'antd';
import { Link } from 'dva/router';
import Cookies from 'js-cookie';
import styles from './Header.less';
import logo from '../../assets/logo.png';
import userImg from '../../assets/user-head.png';

function Header({ dispatch, currentNavigation, userRealName, logOut, userId }) {
  // const logOut = Cookies.get('logOut');
  /**
  *切换导航栏选中被切换的导航
  *@param item 被点击的元素
  */
  function onChangeNavigation(item) {
    dispatch({
      type: 'app/changeNavigation',
      payload: {
        currentNavigation: item.key,
      },
    });
  }
  const menuUser = (
    <Menu>
      <Menu.Item>
        <a target="" rel="noopener noreferrer" href={logOut}>退出</a>
      </Menu.Item>
    </Menu>
  );
  return (
    <div className={styles.header}>
      <div className={styles.content}>
        <div className={styles.logo}>
          <span />
          <img src={logo} title="地点管理" alt="地点管理" />
        </div>
        <div className={styles.name}>
          <h1>地点管理</h1>
          <span>Location Management</span>
        </div>
        <div className={styles.menu}>
          <Menu
            selectedKeys={[currentNavigation]}
            mode="horizontal"
            theme="dark"
            onClick={onChangeNavigation}
          >
            {/*
            <Menu.Item key="basics">
              <Link to={{ pathname: '/', query: { userRealName, userId, logOut } }}>基础信息</Link>
            </Menu.Item>
            */}
            <Menu.Item key="place">
              <Link to={{ pathname: '/place', query: { userRealName, userId, logOut } }}>地点管理</Link>
            </Menu.Item>
            {
              /* <Menu.Item key="classroomtype">
              <Link to={{ pathname: '/classroomtype', query: { userRealName, userId, logOut } }}>教室类型</Link>
            </Menu.Item>*/}
          </Menu>
        </div>
        <div className={styles.user}>
          <Dropdown overlay={menuUser}>
            <a className="ant-dropdown-link" href="javascript:void(0)">
              <span className={styles.userImgBorder}>
                <img src={userImg} className={styles.userImg} />
              </span>
              {userRealName}
              <Icon type="down" />
            </a>
          </Dropdown>
        </div>
      </div>
    </div>
  );
}

function mapStateToProps(state) {
  const { currentNavigation } = state.app;
  return { currentNavigation };
}

export default connect(mapStateToProps)(Header);
