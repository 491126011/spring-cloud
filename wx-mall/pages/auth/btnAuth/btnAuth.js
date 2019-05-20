const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');

//获取应用实例
const app = getApp()
Page({
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    navUrl: '',
    code: ''
  },

  onLoad: function(options) {
    let type = options.type;
    let that = this;
    if (wx.getStorageSync("navUrl")) {
      that.setData({
        navUrl: wx.getStorageSync("navUrl")
      })
    } else {
      that.setData({
        navUrl: '/pages/index/index'
      })
    }

    //检测到后台token失效后(返回值401),重新发起微信登录wx.login
    if (type = 401) {
      wx.login({
        success: function (res) {
          if (res.code) {
            that.setData({
              code: res.code
            })
          }
        }
      });
    }else{
      let userInfo = wx.getStorageSync('userInfo');
      let token = wx.getStorageSync('token');
      let timeout = wx.getStorageSync('tokenExpire');

      if (userInfo && token && checkExpire(timeout)) {
          return;
      }else{
        wx.login({
          success: function (res) {
            if (res.code) {
              that.setData({
                code: res.code
              })
            }
          }
        });
      }
    }
  },

  bindGetUserInfo: function(e) {
    let that = this;
    //登录远程服务器,code通过用户授权后获得。
    if (that.data.code) {
      util.request(api.AuthLoginByWeixin, {
        code: that.data.code,
        userInfo: e.detail
      }, 'POST', 'application/json').then(res => {
        if (res.errno === 0) {
          let timeout = Date.now() + 1000 * 60 * 60 * 12;
          //存储用户信息
          wx.setStorageSync('userInfo', res.data.userInfo);
          wx.setStorageSync('token', res.data.token);
          wx.setStorageSync('userId', res.data.userId); 
          //有效期
          wx.setStorageSync('userInfoExpire', timeout);
          wx.setStorageSync('tokenExpire', timeout);
        } else {
          // util.showErrorToast(res.errmsg)
          wx.showModal({
            title: '提示',
            content: res.errmsg,
            showCancel: false
          });
        }
      });
    }
    if (that.data.navUrl && that.data.navUrl == '/pages/index/index') {
      wx.switchTab({
        url: that.data.navUrl,
      })
    } else if (that.data.navUrl) {
      wx.redirectTo({
        url: that.data.navUrl,
      })
    }
  },
  onReady: function() {
    // 页面渲染完成
  },
  onShow: function() {
    // 页面显示
  },
  onHide: function() {
    // 页面隐藏
  },
  onUnload: function() {
    // 页面关闭
  }
})