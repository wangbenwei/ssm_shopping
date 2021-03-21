(function (w, d, u) {
    var form = util.get('form');
    if (!form) {
        return;
    }
    var title = form['name'];
    var summary = form['subTitle'];
    var image = form['picture'];
    var detail = form['detail'];
    var price = form['price'];
    var uploadInput = form['file'];
    var isSubmiting = false;
    var imgpre = util.get('imgpre');
    var loading = new Loading();
    var imageUrl;
    var imageMode = "urlUpload";


    var page = {
        init: function () {
            var $ = function (id) {
                return document.getElementById(id);
            };

            $('uploadType').onclick = function (e) {
                e = window.event || e;  //这句话是为了实现多种浏览器兼容，用于区分IE和其他浏览器
                o = e.srcElement || e.target;  //这句话的功能是鼠标点击出现下拉菜单，点击别处就缩回去
                // 在这里点击图片地址就出现图片地址的输入框，点击本地上传就出现选择文件和上传的按钮

                //我感觉下面这个if条件就是做选择，选择在前端出现哪个菜单：是网络上传还是本地上传
                if (o.nodeName === "INPUT") {
                    var s, h;
                    o.value === 'url' ? (s = 'urlUpload', h = 'fileUpload') : (s = 'fileUpload', h = 'urlUpload');
                    imageMode = o.value === 'url' ? "urlUpload" : "fileUpload";
                    image.classList.remove("z-err");
                    uploadInput.classList.remove("z-err");
                    $(s).style.display = 'block';
                    $(h).style.display = 'none';
                }
            };

            $('upload').addEventListener('click', function () {

                uploadInput.addEventListener('change', function () {
                    console.log(uploadInput.files) // File listing!
                });
                for (var i = 0, fileCount = uploadInput.files.length; i < fileCount; i++) {
                    console.log(uploadInput.files[i]);
                }
                var maxAllowedSize = 1000000;
                var file = uploadInput.files[0];

                if (uploadInput.files[0].size > maxAllowedSize) {
                    alert("超过文件上传大小限制");
                } else {
                    var form = new FormData();
                    form.append('file', file, file.name);
                    form.enctype = "multipart/form-data";
                    var xhr = new XMLHttpRequest();
                    xhr.open("post", "upload", true);
                    xhr.onload = function () {
                        if (xhr.status === 200) {
                            alert("文件上传成功");
                            var o = JSON.parse(xhr.responseText);  //把json串转成对象
                            imageUrl = o && o.result; //从对象中取出result属性的值
                            image.value = imageUrl;
                            imgpre.src = imageUrl;
                        } else {
                            alert('An error occurred!');
                        }
                    };
                    xhr.send(form);
                }
            });
            form.addEventListener('submit', function (e) {
                if (!isSubmiting && this.check()) {
                    price.value = Number(price.value);
                    isSubmiting = true;
                    form.submit();
                }
            }.bind(this), false);
            [title, summary, image, detail, price].forEach(function (item) {
                item.addEventListener('input', function (e) {
                    item.classList.remove('z-err');
                }.bind(this), false);
            }.bind(this));
            image.addEventListener('input', function (e) {
                var value = image.value.trim();
                if (value != '') {
                    imgpre.src = value;
                }
            }.bind(this), false);
        },
        check: function () {
            var result = true;
            [
                [title, function (value) {
                    return value.length < 2 || value.length > 80
                }],
                [summary, function (value) {
                    return value.length < 2 || value.length > 140
                }],
                [image, function (value) {
                    return imageMode == "urlUpload" && value == ''
                }],
                [detail, function (value) {
                    return value.length < 2 || value.length > 1000
                }],
                [price, function (value) {
                    return value == '' || !Number(value)
                }]
            ].forEach(function (item) {
                var value = item[0].value.trim();
                if (item[1](value)) {
                    item[0].classList.add('z-err');
                    result = false;
                }
                item[0].value = value;
            });
            // if (imageMode == "fileUpload" && !imageUrl) {
            //     uploadInput.classList.add('z-err');
            //     result = false;
            // }
            return result;
        }
    };
    page.init();
})(window, document);