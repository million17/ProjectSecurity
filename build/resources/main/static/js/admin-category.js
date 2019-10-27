$(document).ready(function () {


    $(".del-category").on("click",function () {
        var categoryId= $(this).data("category");
        console.log(categoryId);
        var linkPost = "/api/category/delete/" + categoryId;

        swal({
            title: 'Bạn có chắc chắn muốn xóa danh mục?',
            text: "Bạn sẽ không có khả năng khôi phục!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xóa',
            cancelButtonText:'Hủy'
        }).then(function (result) {
            if (result.value){
                NProgress.start();
                axios.post(linkPost),then(function (res) {
                    NProgress.done();
                    if (res.data.success){
                        swal(
                            {
                                title:'Thành công',
                                text:'Xóa thành công',
                                type:'success',
                                showCancelButton: false,
                                timer:1500
                            }
                        ).then(function () {
                            location.reload();
                        });
                    }else{
                        swal(
                            'Error',
                            res.data.message,
                            'error'
                        );
                    }

                },function (err) {
                    NProgress.done();
                    swal(
                        'Thất bại',
                        'Lỗi ghi dữ liệu',
                        'error'
                    );
                });
            }
        });

    });

    var dataCategory = {};

    $("#new-category").on("click",function () {
        dataCategory = {};
        $('#input-category-name').val("");
        $('#input-category-desc').val("");
    });


    $(".edit-category").on("click",function () {
        /*var pdInfo = $(this).data("category");
        console.log(pdInfo);
        NProgress.start();
        axios.get("/api/category/detail/" + pdInfo).then(function (res) {
            NProgress.done();
            if (res.data.success){
                dataCategory.id = res.data.data.id;
                $('#input-category-name').val(res.data.data.name);
                $('#input-category-desc').val(res.data.data.shortDesc);

            } else {
                console.log("ahuhu");
            }

        },function (err) {
            NProgress.done();

        })*/
        //ajax
        var categoryId = $(this).data("category");
        console.log(categoryId);
        $.ajax({
            url:"/api/category/detail",
            dataType:'json',
            type: 'POST',
            data: {
                categoryId : categoryId
            },
            success: function (data) {
                console.log(data);
                $("#input-category-id").val(data.id);
                $("#input-category-name").val(data.name);
                $("#input-category-desc").val(data.shortDesc);
            },
            error: function (xhr,status,err) {
                console.log(err.toString());

            }
        })
    });


    $(".btn-save-category").on("click",function () {
        if ($("#input-category-desc").val() ==="" || $("#input-category-name").val() === ""){
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }
        var category = {};
        category.id = $("#input-category-id").val();
        category.name= $('#input-category-name').val();
        category.shortDesc = $('#input-category-desc').val();

        // console.log(category.id);
        var linkPost = "/api/category/create";
        if (category.id){
            linkPost = "/api/category/update/" +category.id;

        }
        NProgress.start();
        axios.post(linkPost, category).then(function (res) {
            NProgress.done();
            if (res.data.success){
                swal(
                    'Good job!',
                    res.data.message,
                    'success'
                ).then(function () {
                    location.reload();
                });
            }else {
                swal(
                    'Error',
                    res.data.message,
                    'error'
                );
            }

        },function (err) {
            NProgress.done();
            swal(
                'Error',
                'Some error when saving category',
                'error'
            );

        })

    });
});