// ajax post请求
function ajaxPost(url, param, callBack) {
    $.ajax({
        async : false,
        type: "POST",
        dataType: "json",
        contentType : "application/json",
        data : JSON.stringify(param),
        url: url,
        success:function(data) { // 请求成功后处理函数。
            callBack(data);
        } ,
        error: function (e) { // 请求失败处理函数
            console.log(e);
        }
    });
}

function tableCol(flag, userName) {
    // 列内容
    var col = [
        {
            field: 'id',
            title: '主键',
            align: 'center'
        }, {
            field: 'sqNum',
            title: '工单编号',
            align: 'center'
        }, {
            field: 'userName',
            title: '申请人',
            align: 'center'
        }, {
            field: 'processName',
            title: '流程名称',
            align: 'center'
        }, {
            field: 'startTime',
            title: '申请日期',
            align: 'center',
            formatter: function (value, row, index) {
                return dateFormat(value);
            }
        }, {
            field: 'status',
            title: '工单状态',
            align: 'center',
            formatter: function (value, row, index) {
                if (value == 0) {
                    return '草稿';
                } else if (value == 1) {
                    return '审批中';
                } else if (value == 2) {
                    return '不通过';
                } else if (value == 3) {
                    return '通过';
                } else {
                    return '-未知-';
                }
            }
        }, {
            field: 'control',
            title: '操作',
            align: 'center',
            width: '160',
            formatter: function (value, row, index) {
                var a = '';
                if (flag == 'sp') {
                    a += '<a class="label label-table label-success" href="javascript:void(0);" onclick="approvalProcess(\'' + row.id + '\', \'' + row.taskId + '\')">审批</a>&nbsp;';
                    a += '<a class="label label-table label-info" href="javascript:void(0);" onclick="moveProcess(\'' + row.id + '\')">流转</a>';
                    return a;
                }

                if (row.status == 0) {
                    a += '<a class="label label-table label-success" onclick="startProcess(\'' + row.id + '\')" href="javascript:void(0);" >提交</a>&nbsp;';
                    a += '<a class="label label-table label-danger" onclick="deleteData(\'' + row.id + '\')" href="javascript:void(0);" >删除</a>';
                } else if (row.status == 1) {
                    a += '<a class="label label-table label-danger" href="javascript:void(0);" onclick="backProcess(\'' + row.id + '\')" >撤回</a>&nbsp;';
                    a += '<a class="label label-table label-info" href="javascript:void(0);" onclick="moveProcess(\'' + row.id + '\')">流转</a>';
                } else {
                    a += '<a class="label label-table label-info" href="javascript:void(0);" onclick="moveProcess(\'' + row.id + '\')">流转</a>';
                }
                return a;
            }
        }];

    if (flag == 'sp') {
        col.splice(5, 0, {
            field: 'spName',
            title: '审批人',
            align: 'center'
        });
    }

    return col;
}


// table 初始
function tableOpt(url, para, flag) {
    var opt = {
        // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        cache: false,
        // 本地化
        locale: "zh-CN",
        // 请求方法
        method: 'post',
        // 可供选择的每页的行数（*）
        pageList: [10, 25, 50],
        // 每页的记录行数（*）
        pageSize: 10,
        // 是否显示分页（*）
        pagination: true,
        // 搜索框按回车开始搜索
        searchOnEnterKey: true,
        // 分页方式：client客户端分页，server服务端分页（*）
        sidePagination: "server",
        // 默认false，设为true则允许复选框仅选择一行
        singleSelect: true,
        // 设为true，开启精确搜索
        strictSearch: true,
        // 是否显示行间隔色
        striped: true,
        //是否启用排序
        sortable: true,
        //排序方式
        sortOrder: "asc",
        // 查询参数,每次调用是会带上这个参数，可自定义
        queryParams: function (params) {
            return {
                pageNumber: params.offset / params.limit + 1,
                pageSize: params.limit,
                userName: para.userName,
                processName: para.processName
            };
        },
        // 每列展示信息设置
        columns: tableCol(flag, para.userName),
        // 表明哪个是字段是主键
        idField: 'id',
        // 是否显示搜索
        search: false,
        // 搜索框展示信息
        searchText: "",
        // 这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
        url: url,
        rowStyle: rowStyle
    };
    return opt;
}

// 行颜色
function rowStyle(row, index) {
    var classes = ['active', 'success', 'info', 'warning', 'danger'];
    if (index % 2 === 0 && index / 2 < classes.length) {
        return {
            classes: classes[index / 2]
        };
    }
    return {};
}

// 日期格式化
function dateFormat(value) {
    if(value == null || value == undefined){
        return "";
    }
    var date = new Date(value);

    var Y = date.getFullYear(),
        m = date.getMonth()+1,
        d = date.getDate(),
        H = date.getHours(),
        i = date.getMinutes(),
        s = date.getSeconds();

    return Y + '-' + m + '-' + d + ' ' + H + ':' + i + ':' + s;
}