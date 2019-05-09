$(function () {
    console.log("xcad");
    vm.getInfo();
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        statistics: {},
        q: {
            sendId: ''
        }
    },
    methods: {
        getInfo: function () {
            Ajax.request({
                url: "../sys/main/info",
                async: true,
                successCallback: function (r) {
                    console.log(r.statistics.countTradeMoney);
                    vm.statistics = r.statistics;
                }
            });
        }
    }
});