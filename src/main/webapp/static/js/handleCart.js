// var num = document.getElementById('number').value;
// 增加商品数量函数
function add(id){   //id是字符串 
    var getid = id;
    var num = document.getElementById(getid).value;
    if(num>-1){
        num++;
        document.getElementById(getid).value = num;
    }
    // console.log(num)
}
// 删减商品数量函数
function mod(id){
    var getid = id;
    var num = document.getElementById(getid).value;
    if(num<1){
        return
    }else{
        num--;
        document.getElementById(getid).value = num;
    }
    // console.log(num)
}

// 详情页的丁点击加入购物车
$('#btnAddCart').click(function(){
    alert('加入成功！')
})
// 详情页的点击立即购买
 $('#btnBuy').click(function(){
     alert('购买成功！')
 })