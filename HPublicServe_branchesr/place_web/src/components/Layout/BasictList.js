/** ***************
基础信息
  @author 范丽臣
******************/
import React from 'react';
import { Row, Col, Icon, message, Upload } from 'antd';
import styles from './BasictList.less';
import BasictListSchoolSetting from '../Table/BasictListSchoolSetting.js';
import BasictListParagraphSetting from '../Table/BasictListParagraphSetting.js';
import config from '../../utils/config.js';

/**
*切换导航栏选中被切换的导航
*@param GetLogoInfoData 图片数据
*@param handleChange 上传图片参数
*@param modifyModal 修改基本信息参数
*@param result 基本信息数据
*@param SchoolModal 分校弹窗参数
*@param ParModal 学段弹窗参数
*@param Schooldata 修改显示的数据
*@param DeleteSchoolItem 删除校区参数
*@param EditSchoolItem 修改校区参数
*@param StudySectiondata 学段数据
*@param EditSectionItem 修改学段
*@param DeleteSectionItem 删除

*/
function BasictList({ GetLogoInfoData, handleChange, modifyModal, result, SchoolModal, ParModal, Schooldata, DeleteSchoolItem, EditSchoolItem, StudySectiondata, EditSectionItem, DeleteSectionItem }) {
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

  function beforeUpload(file) {
    if (file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/gif' || file.type === 'image/jpg') {
      const isLt = file.size / 1024 / 1024 < 0.15;
      if (!isLt) {
        message.error('请上传小于150KB图片!');
        return false;
      }
      return isLt && file.type;
    } else {
      message.error('请上传png、jpeg、gif、jpg格式图片!');
      return false;
    }
  }
  let imageUrl = '';
  if (GetLogoInfoData.length > 0) {
    if (GetLogoInfoData[0].urls.length != 0) {
      // imageUrl += 'http://';
      imageUrl += config.imgIp;
      if (GetLogoInfoData[0].urls[0].pic_url) {
        imageUrl += GetLogoInfoData[0].urls[0].pic_url.split(':')[1];
      }
    }
  } else {
    imageUrl = '';
  }
  return (

    <div className={styles.main}>
      <Row>
        <Col span={24}>
          <div className={styles.list}>
            <span>基本信息</span>
            <Icon type="exception" style={{ fontSize: '20px' }} onClick={modifyModal} />
          </div>
        </Col>

        <div className={styles.listInfo}>
          <div className={styles.upload}>
            <Upload
              className="avatar-uploader"
              name="avatar"
              multiple
              showUploadList={false}
              action={config.resourceService}
              beforeUpload={beforeUpload}
              onChange={handleChange}
            >
              {
                imageUrl ?
                  <div className={styles.uploadhover} style={{ position: 'relative' }}>

                    <img src={imageUrl} alt="" className="avatar" />
                    <Icon type="plus" className="avatar-uploader-trigger" style={{ position: 'absolute', left: '0', top: '0' }} />
                  </div> :
                  <Icon type="plus" className="avatar-uploader-trigger" />
              }
            </Upload>
          </div>


          <div className={styles.basictInfo}>

            <Row className={styles.rowList} style={{ borderBottom: '1px solid #a8a8a8' }}>
              <Col span={8}><p>学校名称：</p><span title={cn_name}>{cn_name}</span></Col>
              <Col span={8}><p>英文名称：</p><span title={en_name}>{en_name}</span></Col>
              <Col span={8}><p>地区：</p><span title={region}>{region}</span></Col>
            </Row>

            <Row className={styles.rowList} style={{ borderBottom: '1px solid #a8a8a8' }}>
              <Col span={8}><p>地址：</p><span title={location}>{location}</span></Col>
              <Col span={8}><p>电话：</p><span title={phone}>{phone}</span></Col>
              <Col span={8}><p>传真：</p><span title={fax}>{fax}</span></Col>
            </Row>

            <Row className={styles.rowList}>
              <Col span={8}><p>邮编：</p><span title={postcode}>{postcode}</span></Col>
              <Col span={8}><p>邮箱：</p><span title={mail}>{mail}</span></Col>
              <Col span={8}><p>网址：</p><span title={website}>{website}</span></Col>
            </Row>
          </div>


        </div>
      </Row>


      <Row>
        <Col span={24}>
          <div className={styles.list}>
            <span>校区设置</span>
            <Icon type="plus-square-o" style={{ fontSize: '20px' }} onClick={SchoolModal} />
          </div>
          <BasictListSchoolSetting Schooldata={Schooldata} DeleteSchoolItem={DeleteSchoolItem} EditSchoolItem={EditSchoolItem} />
        </Col>
      </Row>

      <Row>
        <Col span={24}>
          <div className={styles.list}>
            <span>学段设置</span>
            <Icon type="plus-square-o" style={{ fontSize: '20px' }} onClick={ParModal} style={{ display: 'none' }} />
          </div>
          <BasictListParagraphSetting StudySectiondata={StudySectiondata} EditSectionItem={EditSectionItem} DeleteSectionItem={DeleteSectionItem} />
        </Col>
      </Row>

    </div>

  );
}

export default BasictList;
