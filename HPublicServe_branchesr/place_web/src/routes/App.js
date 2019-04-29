import React from 'react';
import { connect } from 'dva';
import { Layout } from 'antd';
import Header from '../components/Header/Header.js';
import Footer from '../components/Footer/Footer.js';
// import styles from './App.less';

const { Content } = Layout;

function App({ children, app }) {
  const { userRealName, userId, logOut } = app;
  const headerProps = {
    userRealName,
    userId,
    logOut,
  };
  return (
    <Layout>
      <Header {...headerProps} />
      <Content style={{ width: '1232px', margin: '16px auto 0', padding: '0 0 16px', background: '#fff' }}>
        {children}
      </Content>
      <Footer />
    </Layout>
  );
}

function mapStateToProps({ app }) {
  return { app };
}

export default connect(mapStateToProps)(App);
