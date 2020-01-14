// 登录封装方法
function login() {
    var username = document.getElementById('userName').value;
    var password = document.getElementById('password').value;
    var validatecode = document.getElementById('validateCode').value;
    // 用户名正则：4到16位（字母，数字，下划线，减号）： \w匹配字母数字及下划线
    // var user = /^[a-zA-Z]\w{3,15}$/;
    var user = /^([a-zA-Z_]){1,4}([1-9_]){2,4}$/;
    var checkusername = user.test(username);
    if (checkusername) {
        // 密码强度正则：最少6位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符：
        var pwd = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{6,}$/;
        var checkpassword = pwd.test(password)
        if (checkpassword) {
            var code = /^[1-9]{4,6}$/   //[1-9]==\d
           var checkvalidatecode = code.test(validatecode)
           console.log(checkvalidatecode)
           if(checkvalidatecode){
            //    alert("登录成功！")
               location.href = 'home.html'
           }else{
               alert('请输入正确的验证码')
           }
        } else {
            alert('请输入正确的密码')
            document.getElementById('password').value = ""
        }
    } else {
        alert('请输入正确的用户名')
        document.getElementById('userName').value = ""
    }

    //将用户名存入本地存储中
    var setusername = JSON.stringify(username)
    sessionStorage.setItem('username',setusername)   


}
