$(function () {
    $("#jqGrid").Grid({
        url: '../statistics/list',
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '统计时间', name: 'countDate', index: 'count_date', width: 80},
            {label: '当日注册用户', name: 'todayRegUser', index: 'today_reg_user', width: 80},
            {label: '当日微信用户', name: 'todayWxRegUser', index: 'today_wx_reg_user', width: 80},
            {label: '当日下单新用户', name: 'todayOrderUser', index: 'today_order_user', width: 80},
            {label: '系统总用户数', name: 'countUserNum', index: 'count_user_num', width: 80},
            {label: '当日支付订单数', name: 'todayPayOrder', index: 'today_pay_order', width: 80},
            {label: '当日未支付订单数', name: 'todayUnpayOrder', index: 'today_unpay_order', width: 80},
            {label: '当日下单用户数', name: 'todayUserOrder', index: 'today_user_order', width: 80},
            {label: '系统累计成交订单', name: 'countOrderNum', index: 'count_order_num', width: 80},
            {label: '当日交易金额', name: 'todayTradeMoney', index: 'today_trade_money', width: 80},
            {label: '累计交易金额', name: 'countTradeMoney', index: 'count_trade_money', width: 80}]
    });
});

let vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        statistics: {},
        ruleValidate: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            name: ''
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.statistics = {};
        },
        update: function (event) {
            let id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            let url = vm.statistics.id == null ? "../statistics/save" : "../statistics/update";
            Ajax.request({
                url: url,
                params: JSON.stringify(vm.statistics),
                type: "POST",
                contentType: "application/json",
                successCallback: function (r) {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });
        },
        del: function (event) {
            let ids = getSelectedRows("#jqGrid");
            if (ids == null){
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    url: "../statistics/delete",
                    params: JSON.stringify(ids),
                    type: "POST",
                    contentType: "application/json",
                    successCallback: function () {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    }
                });
            });
        },
        getInfo: function(id){
            Ajax.request({
                url: "../statistics/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.statistics = r.statistics;
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        reloadSearch: function() {
            vm.q = {
                name: ''
            }
            vm.reload();
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        }
    }
});