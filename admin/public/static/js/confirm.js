Confirm = {
    show : function (message,callback) {

        Swal.fire({
            title: '确认?',
            text: message,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '确定'

        }).then((result) => {
            console.log("------删除删除：：：：："+result.value);
            //发送请求删除
            if(result.value){
                if (callback) {
                    callback()
                }
            }
        })





    }
}