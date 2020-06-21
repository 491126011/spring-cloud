var app = getApp();
var WxParse = require('../../lib/wxParse/wxParse.js');
var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
  data: {
    winHeight: "",
    id: 0,
    goods: {},
    gallery: [],
    attribute: [],
    issueList: [],
    comment: [],
    brand: {},
    specificationList: [],
    productList: [],
    relatedGoods: [],
    cartGoodsCount: 0,
    userHasCollect: 0,
    number: 1,
    checkedSpecText: '请选择规格数量',
    goodsPrice: 0,
    product: {},
    openAttr: false,
    noCollectImage: "/static/images/icon_collect.png",
    hasCollectImage: "/static/images/icon_collect_checked.png",
    collectBackImage: "/static/images/icon_collect.png",
    qrCodeTempLocation: '',
    imagePath: '',
    canvasHidden: false,
    maskHidden: false,
    sharePicture : 'http://cdn.siiika.com/upload/20200514/225111259b1294.png',
    showShareView : false

  },
  getGoodsInfo: function () {
    let that = this;
    util.request(api.GoodsDetail, {
      id: that.data.id
    }).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          goods: res.data.info,
          gallery: res.data.gallery,
          attribute: res.data.attribute,
          issueList: res.data.issue,
          comment: res.data.comment,
          brand: res.data.brand,
          specificationList: res.data.specificationList,
          productList: res.data.productList,
          userHasCollect: res.data.userHasCollect,
          goodsPrice: res.data.info.retail_price,
        });
        //设置默认值
        that.setDefSpecInfo(that.data.specificationList);
        if (res.data.userHasCollect == 1) {
          that.setData({
            'collectBackImage': that.data.hasCollectImage
          });
        } else {
          that.setData({
            'collectBackImage': that.data.noCollectImage
          });
        }

        WxParse.wxParse('goodsDetail', 'html', res.data.info.goods_desc, that);
        that.getGoodsRelated();
      }
    });

  },
  getGoodsRelated: function () {
    let that = this;
    util.request(api.GoodsRelated, {
      id: that.data.id
    }).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          relatedGoods: res.data.goodsList,
        });
      }
    });

  },
  clickSkuValue: function (event) {
    let that = this;
    let specNameId = event.currentTarget.dataset.nameId;
    let specValueId = event.currentTarget.dataset.valueId;

    //判断是否可以点击

    //TODO 性能优化，可在wx:for中添加index，可以直接获取点击的属性名和属性值，不用循环
    let _specificationList = this.data.specificationList;
    for (let i = 0; i < _specificationList.length; i++) {
      if (_specificationList[i].specification_id == specNameId) {
        for (let j = 0; j < _specificationList[i].valueList.length; j++) {
          if (_specificationList[i].valueList[j].id == specValueId) {
            //如果已经选中，则反选
            if (_specificationList[i].valueList[j].checked) {
              _specificationList[i].valueList[j].checked = false;
            } else {
              _specificationList[i].valueList[j].checked = true;
            }
          } else {
            _specificationList[i].valueList[j].checked = false;
          }
        }
      }
    }

    //选择规格后调整对应价格
    let checkedProduct = this.getCheckedProductItem(this.getCheckedSpecKey())
    if (checkedProduct[0]) {
      this.setData({
        goodsPrice: checkedProduct[0].retail_price
      });
    }

    this.setData({
      'specificationList': _specificationList
    });
    //重新计算spec改变后的信息
    this.changeSpecInfo();

    //重新计算哪些值不可以点击
  },

  //获取选中的规格信息
  getCheckedSpecValue: function () {
    let checkedValues = [];
    let _specificationList = this.data.specificationList;
    for (let i = 0; i < _specificationList.length; i++) {
      let _checkedObj = {
        nameId: _specificationList[i].specification_id,
        valueId: 0,
        valueText: ''
      };
      for (let j = 0; j < _specificationList[i].valueList.length; j++) {
        if (_specificationList[i].valueList[j].checked) {
          _checkedObj.valueId = _specificationList[i].valueList[j].id;
          _checkedObj.valueText = _specificationList[i].valueList[j].value;
        }
      }
      checkedValues.push(_checkedObj);
    }
    return checkedValues;
  },
  //根据已选的值，计算其它值的状态
  setSpecValueStatus: function () {

  },
  //判断规格是否选择完整
  isCheckedAllSpec: function () {
    return !this.getCheckedSpecValue().some(function (v) {
      if (v.valueId == 0) {
        return true;
      }
    });
  },
  getCheckedSpecKey: function () {
    let checkedValue = this.getCheckedSpecValue().map(function (v) {
      return v.valueId;
    });
    return checkedValue.join('_');
  },
  changeSpecInfo: function () {
    let checkedNameValue = this.getCheckedSpecValue();

    //设置选择的信息
    let checkedValue = checkedNameValue.filter(function (v) {
      if (v.valueId != 0) {
        return true;
      } else {
        return false;
      }
    }).map(function (v) {
      return v.valueText;
    });
    if (checkedValue.length > 0) {
      this.setData({
        'checkedSpecText': checkedValue.join('　')
      });
    } else {
      this.setData({
        'checkedSpecText': '请选择规格数量'
      });
    }
  },
  getCheckedProductItem: function (key) {
    return this.data.productList.filter(function (v) {
      if (v.goods_specification_ids.indexOf(key) > -1) {
        return true;
      } else {
        return false;
      }
    });
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    if (options.scene) {
      let scene = decodeURIComponent(options.scene)
      wx.setStorageSync('sellerId',scene.sellerId || 10010)
      this.setData({
        // id: parseInt(scene.split('=')[1])
        id: parseInt(scene.id)
      });
    } else {
      wx.setStorageSync('sellerId',options.sellerId || 10010)
      this.setData({
        id: parseInt(options.id)
      });
    }
    var that = this;
    this.getGoodsInfo();
    util.request(api.CartGoodsCount).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          cartGoodsCount: res.data.cartTotal.goodsCount
        });

      }
    });

    var that = this
    //  高度自适应
    wx.getSystemInfo({
      success: function (res) {
        var clientHeight = res.windowHeight,
          clientWidth = res.windowWidth,
          rpxR = 750 / clientWidth;
        var calc = clientHeight * rpxR - 100;
        that.setData({
          winHeight: calc
        });
      }
    });
  },
  onReady: function () {
    // 页面渲染完成

  },
  onShow: function () {
    // 页面显示

  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  },
  switchAttrPop: function () {
    if (this.data.openAttr == false) {
      this.setData({
        openAttr: !this.data.openAttr,
        collectBackImage: "/static/images/detail_back.png"
      });
    }
  },
  closeAttrOrCollect: function () {
    let that = this;
    if (this.data.openAttr) {
      this.setData({
        openAttr: false,
      });
      if (that.data.userHasCollect == 1) {
        that.setData({
          'collectBackImage': that.data.hasCollectImage
        });
      } else {
        that.setData({
          'collectBackImage': that.data.noCollectImage
        });
      }
    } else {
      //添加或是取消收藏
      util.request(api.CollectAddOrDelete, {
        typeId: 0,
        valueId: this.data.id
      }, "POST", "application/json")
        .then(function (res) {
          let _res = res;
          if (_res.errno == 0) {
            if (_res.data.type == 'add') {
              that.setData({
                'collectBackImage': that.data.hasCollectImage
              });
            } else {
              that.setData({
                'collectBackImage': that.data.noCollectImage
              });
            }

          } else {
            wx.showToast({
              image: '/static/images/icon_error.png',
              title: _res.errmsg,
              mask: true
            });
          }
        });
    }
  },
  openCartPage: function () {
    wx.switchTab({
      url: '/pages/cart/cart',
    });
  },

  /**
   * 直接购买
   */
  buyGoods: function () {
    var that = this;
    if (this.data.openAttr == false) {
      //打开规格选择窗口
      this.setData({
        openAttr: !this.data.openAttr,
        collectBackImage: "/static/images/detail_back.png"
      });
    } else {

      //提示选择完整规格
      if (!this.isCheckedAllSpec()) {
        return false;
      }

      //根据选中的规格，判断是否有对应的sku信息
      let checkedProduct = this.getCheckedProductItem(this.getCheckedSpecKey());
      if (!checkedProduct || checkedProduct.length <= 0) {
        //找不到对应的product信息，提示没有库存
        wx.showToast({
          title: '找不到对应产品信息',
          icon: 'none'
        });
        return false;
      }

      //验证库存
      if (checkedProduct.goods_number < this.data.number) {
        //找不到对应的product信息，提示没有库存
        wx.showToast({
          title: '选定的产品已售罄',
          icon: 'none'
        });
        return false;
      }

      // 直接购买商品
      util.request(api.BuyAdd, {
        goodsId: this.data.goods.id,
        number: this.data.number,
        productId: checkedProduct[0].id
      }, "POST", 'application/json')
        .then(function (res) {
          let _res = res;
          if (_res.errno == 0) {
            that.setData({
              openAttr: !that.data.openAttr,
            });
            wx.navigateTo({
              url: '/pages/shopping/checkout/checkout?isBuy=true',
            })
          } else {
            wx.showToast({
              image: '/static/images/icon_error.png',
              title: _res.errmsg,
              mask: true
            });
          }

        });

    }
  },

  /**
   * 添加到购物车
   */
  addToCart: function () {
    var that = this;
    if (this.data.openAttr == false) {
      //打开规格选择窗口
      this.setData({
        openAttr: !this.data.openAttr,
        collectBackImage: "/static/images/detail_back.png"
      });
    } else {
      //提示选择完整规格
      if (!this.isCheckedAllSpec()) {
        wx.showToast({
          title: '请选择完整规格'
        });
        return false;
      }

      //根据选中的规格，判断是否有对应的sku信息
      let checkedProduct = this.getCheckedProductItem(this.getCheckedSpecKey());
      if (!checkedProduct || checkedProduct.length <= 0) {
        //找不到对应的product信息，提示没有库存
        wx.showToast({
          title: '选定的规格已售罄',
          icon: 'none'
        });
        return false;
      }

      //验证库存
      if (checkedProduct.goods_number < this.data.number) {
        //找不到对应的product信息，提示没有库存
        wx.showToast({
          title: '选定的产品已售罄',
          icon: 'none'
        });
        return false;
      }

      //添加到购物车
      util.request(api.CartAdd, {
        goodsId: this.data.goods.id,
        number: this.data.number,
        productId: checkedProduct[0].id
      }, 'POST', 'application/json')
        .then(function (res) {
          let _res = res;
          if (_res.errno == 0) {
            wx.showToast({
              title: '添加成功'
            });
            that.setData({
              openAttr: !that.data.openAttr,
              cartGoodsCount: _res.data.cartTotal.goodsCount
            });
            if (that.data.userHasCollect == 1) {
              that.setData({
                'collectBackImage': that.data.hasCollectImage
              });
            } else {
              that.setData({
                'collectBackImage': that.data.noCollectImage
              });
            }
          } else {
            wx.showToast({
              image: '/static/images/icon_error.png',
              title: _res.errmsg,
              mask: true
            });
          }

        });
    }

  },
  cutNumber: function () {
    this.setData({
      number: (this.data.number - 1 > 1) ? this.data.number - 1 : 1
    });
  },
  addNumber: function () {
    this.setData({
      number: this.data.number + 1
    });
  },
  setDefSpecInfo: function (specificationList) {
    //未考虑规格联动情况
    let that = this;
    if (!specificationList) return;
    for (let i = 0; i < specificationList.length; i++) {
      let specification = specificationList[i];
      let specNameId = specification.specification_id;
      //规格只有一个时自动选择规格
      if (specification.valueList && specification.valueList.length == 1) {
        let specValueId = specification.valueList[0].id;
        that.clickSkuValue({
          currentTarget: {
            dataset: {
              "nameId": specNameId,
              "valueId": specValueId
            }
          }
        });
      }
    }
    specificationList.map(function (item) { });
  },
  showShare(){
    this.setData({
      showShareView : true
    })
  },
  closeShare(){
    this.setData({
      showShareView : false
    })
  },
  share() {
    this.setData({
      showShareView : false
    })
    wx.showLoading({
      title: '正在生成海报...',
      mask: true
    })
    this.downloadPicture()
  },
  downloadPicture() {
    let that = this
    wx.downloadFile({
      url: api.QrCodeGet,
      header: {
        'X-Nideshop-Token': wx.getStorageSync('token'),
        'params': "id=" + this.data.goods.id + "&sellerId=" + (wx.getStorageSync('sellerId') || 10010),
        'page': 'pages/goods/goods'
      },
      complete(res) {
        if (res.statusCode === 200) {
          let wxCode = res.tempFilePath // 小程序码图片
          that.setData({
            maskHidden: true,
            qrCodeTempLocation: wxCode
          })
          that.drawImage()
        }
      }
    })
  },
  drawImage() {
    let that = this
    let ctx = wx.createCanvasContext('canvasPoster', this)
    ctx.setFillStyle("#FFFFFF")
    ctx.fillRect(0, 0, 375, 540)
    // let canvasW = canvasAttrs.width // 画布的真实宽度
    // let canvasH = canvasAttrs.height //画布的真实高度
    wx.downloadFile({
      url: this.data.goods.list_pic_url,
      complete(res) {
        if (res.statusCode == 200) {
          ctx.drawImage(res.tempFilePath, 0, 0, 375, 375)

          ctx.setFontSize(16);
          ctx.setFillStyle('#000000');
          ctx.fillText(that.data.goods.name, 20, 395);
          ctx.stroke();

          ctx.setFontSize(14);
          ctx.setFillStyle('#666');
          let brief = that.data.goods.goods_brief
          ctx.fillText(brief == null ? '' : brief, 20, 420);
          ctx.stroke();

          ctx.setFontSize(23);
          ctx.setFillStyle('#EE0000');
          let price = that.data.goods.retail_price

          ctx.fillText('￥' + (price == null ? '' : price), 20, 455);
          ctx.stroke();

          // 绘制二维码
          ctx.drawImage(that.data.qrCodeTempLocation, 285, 395, 73, 73)
          // ctx.draw()

          ctx.setFontSize(12);
          ctx.setFillStyle('#666');
          ctx.fillText('长按图片，识别二维码', 240, 482.5);
          ctx.stroke();

          ctx.setFontSize(12);
          ctx.setFillStyle('#666');
          ctx.fillText('查看商品详情', 288, 504);
          ctx.stroke();

          ctx.draw()

          ctx.save()

          setTimeout(() => {
            wx.canvasToTempFilePath({
              canvasId: 'canvasPoster',
              x: 13,
              y: 13,
              width: 375,
              height: 540,
              destWidth: 375,
              destHeight: 540,
              complete: (canvasRes) => {
                let tempFilePath = canvasRes.tempFilePath
                that.setData({
                  imagePath: tempFilePath,
                  canvasHidden: true
                });
                wx.hideLoading()
              }
            })
          }, 300)
        }
      }
    })
  },
  savePoster() {
    let that = this
    if (this.data.imagePath && this.data.imagePath != '') {
      wx.getSetting({
        success(res) {
          if (!res.authSetting['scope.writePhotosAlbum']) {//未授权，则重新授权
            wx.authorize({
              scope: 'scope.writePhotosAlbum',
              success() {
                that.saveAlbumn()
              },
              fail: function (res) {
                wx.hideLoading()
                wx.showToast({
                  title: '您拒绝授权保存图片到相册，无法保存图片及分享到朋友圈',
                  icon: 'none',
                  duration: 4000
                })
              }
            })
          } else {
            that.saveAlbumn()
          }
        }
      })
    }
  },
  saveAlbumn() {
    let that = this
    wx.saveImageToPhotosAlbum({
      filePath: this.data.imagePath,
      success: (result) => {
        wx.hideLoading()
        wx.showToast({
          title: '海报已保存，快去分享到您的圈子吧。',
          icon: 'none'
        })
        that.setData({
          maskHidden: false
        })
      }
    })
  },
  onShareAppMessage: function (res) {
    let sellerId = wx.getStorageSync('sellerId') || 10010
    if (res.from === 'button') {
      // 通过按钮触发
      return {
        title: this.data.goods.name,
        path: '/pages/goods/goods?id=' + this.data.goods.id + '&sellerId=' + sellerId,
        imageUrl: this.data.goods.list_pic_url,
        success: function (res) {
          // 转发成功
          console.log('转发成功')
        },
        fail: function (res) {
          // 转发失败
          console.log('转发失败')
        },
        complete(r){
          console.log(r)
        }
      }
    }
    //通过右上角菜单触发
    return {
      title: this.data.goods.name,
      path: '/pages/goods/goods?id=' + this.data.goods.id + '&sellerId=' + sellerId,
      imageUrl: this.data.goods.list_pic_url
    };
  },
})
