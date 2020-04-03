var main = {
    init : function() {
        var _this = this;

        $('#recommend').on('click',function(){
            _this.recommending();
        });
    },


    recommending : function() {
        var id = $('#id').val();

           $.ajax({
           type: 'POST',
           url: '/recommend/'+id,
           async: true,
           data:id,
           success:function(data){
           console.log(data);
            }
           });
    }
};

main.init();

console.log(boardDto);
