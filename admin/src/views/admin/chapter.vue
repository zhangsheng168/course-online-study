<template>
    <div>
        <p>
            <button class="btn btn-success" v-on:click="add()"> 新增</button>
            &nbsp;
            <button class="btn btn-success" v-on:click="list()"> 刷新</button>
        </p>

        <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="7"></pagination>

        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th >
                    ID
                </th>
                <th >名称</th>
                <th>课程ID</th>
                <th>操作</th>

            </tr>
            </thead>

            <tbody>
            <tr v-for="chapter in chapters">
                <td>{{chapter.id}}</td>
                <td>{{chapter.name}}</td>
                <td>{{chapter.courseId}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">

                        <button v-on:click="edit(chapter)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button v-on:click="del(chapter.id)" class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>

                    </div>

                    <div class="hidden-md hidden-lg">
                        <div class="inline pos-rel">
                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                <li>
                                    <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																			<span class="blue">
																				<i class="ace-icon fa fa-search-plus bigger-120"></i>
																			</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																			<span class="red">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i>
																			</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </tr>


            </tbody>
        </table>
        <!--模态框-->
        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <!--模态框头部-->
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <!--模态框主体-->
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input v-model="chapter.name" class="form-control"  placeholder="名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">课程id</label>
                                <div class="col-sm-10">
                                    <input v-model="chapter.courseId" class="form-control"  placeholder="课程id">
                                </div>
                            </div>
                        </form>
                    </div>
                    <!--模态框底部-->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>


</template>


<script>
    import Pagination from "../../components/pagination";

    export default {
        components: {Pagination},
        name: 'chapter',
        data: function() {
            return {
                chapter : {},
                chapters : []
            }
        },
        mounted() {
            let  _this = this;
            _this.$refs.pagination.size = 5;
            _this.list(1);
            //调用父组件的activeSidebar方法
            //sidebar菜单激活样式一
            //this.$parent.activeSidebar("business-chapter-sidebar");
        },
        methods:{
            list(page){
                let _this = this;
                _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/list',{
                    page: page,
                    size: _this.$refs.pagination.size
                }).then((response)=>{
                     console.log("查询大章列表结果：", response);
                    _this.chapters=response.data.content.list;
                    _this.$refs.pagination.render(page,response.data.content.total);
                })
            },

            add() {
                let _this = this;
                //不需要保留上一次编辑的数据，每次显示清空后的add表单
                _this.chapter = {};
                $("#form-modal").modal("show");
            },

            save(page){
                let _this = this;
                //前端校验
                if(!Validator.require(_this.chapter.name,"名称")
                    || !Validator.require(_this.chapter.courseId,"课程ID")
                    || !Validator.length(_this.chapter.courseId,"课程ID",1,8)){
                    return;
                }
                _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/save',_this.chapter).then(
                    (response)=>{
                        console.log("保存大章",response);
                        if(response.data.success){
                            $("#form-modal").modal("hide");
                            _this.list(1);
                            //提示
                            Toast.success("保存成功!");
                        }else{
                            //提示
                            Toast.warning(response.data.message);
                        }
                    }
                )
            },

            edit(chapter) {
                let _this = this;
                //解决编辑时，数据同步显示在列表中的显示问题
                _this.chapter = $.extend({},chapter);
                $("#form-modal").modal("show");
            },

            del(id){
                let _this = this;
                Confirm.show("删除大章后不可恢复,确认删除?",function () {
                    _this.$ajax.delete('http://127.0.0.1:9000/business/admin/chapter/delete/'+id).then(
                        (response)=>{
                            console.log("删除大章",response);
                            if(response.data.success){
                                //弹出提示
                                Toast.success("删除成功!");
                                //刷新页面
                                _this.list(1);
                            }
                        }
                    )
                })

                //显示sweet2弹出框
                /*Swal.fire({
                    title: '确认删除该记录?',
                    text: "删除后,记录不可恢复!",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '确定',
                    cancelButtonText: '取消'
                }).then((result) => {
                    console.log(result);
                    if(result.value){
                        //发送请求删除
                        _this.$ajax.delete('http://127.0.0.1:9000/business/admin/chapter/delete/'+id).then(
                            (response)=>{
                                console.log("删除大章",response);
                                if(response.data.success){
                                    //弹出提示
                                    Toast.success("删除成功!");
                                    //刷新页面
                                    _this.list(1);
                                }
                            }
                        )
                    }


                })
*/





            },

        }
    }
</script>
