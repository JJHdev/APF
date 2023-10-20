$(function(){
  $('#fullpage').fullpage({
		//options here
		autoScrolling:true,
		scrollHorizontally: true,
    navigation:true,
    navigationPosition:'right',
    'afterLoad': function (anchorLink, index) {
			if (index == 1){
				$("[class^='head-']").removeClass('menu-fixed');
			
			}else if (index == 2){
				
			}else if (index == 3){
				
			}else if (index == 4){
				
			}
	 	},
		'onLeave' : function (index, nextIndex, direction){		
			if (index == 1 && direction == 'down'){
				// alert ('1번에서 2번으로');
				$("[class^='head-']").addClass('menu-fixed');
				$('#fp-nav').addClass('trans');
			} else if (index == 1 && direction == 'up'){
				
			}
			if (index == 2 && direction == 'down'){
				// alert ('2번에서 3번으로');
				 $("[class^='head-']").addClass('menu-fixed');
				$('#fp-nav').addClass('trans');
			} else if (index == 2 && direction == 'up'){
				// alert ('2번에서 1번으로');
				
				$('#fp-nav').removeClass('trans');
				console.log('2번에서 1번으로');
				
			}
			if (index == 3 && direction == 'down'){
				// alert ('3번에서 4번으로');
				$("[class^='head-']").addClass('menu-fixed');
				$('#fp-nav').addClass('trans');
			} else if (index == 2 && direction == 'up'){
				// alert ('3번에서 2번으로');
				$("[class^='head-']").addClass('menu-fixed');
	 			$('#fp-nav').removeClass('trans')
			}
		}
	});
  
  
})