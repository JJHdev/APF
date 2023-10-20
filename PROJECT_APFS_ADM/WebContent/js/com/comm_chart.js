/**
******************************************************************************************
*** 파일명    : comm_chart.js
*** 설명      : CHART 관련 사용자 정의 컴포넌트
***
*** -----------------------------    Modified Log   --------------------------------------
*** ver             date                author                  description
*** --------------------------------------------------------------------------------------
*** 1.0         2023.05.11              LSH
******************************************************************************************
**/

const COLORS = {
	BASIC:   ['#4dc9f6','#f67019','#f53794','#537bc4','#acc236','#166a8f','#00a950','#58595b','#8549ba'],
	STANDARD:['#0047BD','#0288D9','#009543','#00AB38','#9AF000','#FFB300','#FFCE00','#FFE63B','#EA0034','#FD4703','#FF822A','#8200AC','#B610BF','#CC72F5']
}

const CHART_COLORS = {
	red:    'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green:  'rgb(75, 192, 192)',
	blue:   'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey:   'rgb(201, 203, 207)'
};
function color(index, code) {
	return COLORS[code][index % COLORS[code].length];
};

function shuffleColors(array) {
	array.sort(function(num1, num2) {
		return Math.random() - 0.5;
	});
	return array;
}

function drawTextBox(ctx, text, x, y, fieldWidth, spacing) {
  var line = "";
  var fontSize = parseFloat(ctx.font);
  var currentY = y;
  ctx.textBaseline = "top";
  for(var i=0; i<text.length; i++) {
    var tempLine = line + text[i];
    var tempWidth = ctx.measureText(tempLine).width;

    if (tempWidth < fieldWidth && text[i] != '\n') {
      line = tempLine;
    }
    else {
      ctx.fillText(line, x, currentY);
      if(text[i] != '\n') line = text[i];
      else line = "";
      currentY += fontSize*spacing;
    }
  }
  ctx.fillText(line, x, currentY);
  ctx.stroke();
}

//===========================================================================//
// Chartjs 공통 유틸 함수
//===========================================================================//
$.chartUtil = {
	// 공통 데이터레이블 옵션
	getLabelOptions: function() {
		return {
			display: true,
			/*
			color:   'black',
			anchor:  'end',
            align:   'start',
			font:    {size:14, weight: 'bold'}
			*/
	        color:   'white',
	        font:    {size:14, weight: 'bold'},
	        offset:  0,
            anchor:  'center',
	        padding: 0
		};
	},
	// 공통 데이터셋레이블 옵션
	getLabelSetOptions: function() {
		return {
			labels: {
				name: {
				  align: 'bottom',
				  font: {size: 14},
				  padding: 10,
				  formatter: function(value, ctx) {
				    return ctx.chart.data.labels[ctx.dataIndex];
				  }
				},
				value: {
				  align: 'top',
				  backgroundColor: 'white',
				  borderColor:     'white',
				  borderWidth:  2,
				  borderRadius: 2,
				  padding:      2,
				  color: function(ctx) {
				    return ctx.dataset.backgroundColor;
				  },
				  formatter: function(value, ctx) {
				    return value+'명';
				  }
				}
			}
		};
	},
	// 공통 타이틀 옵션 (2.9 버전용)
	getTitleOptionsOldVersion: function(title) {
		return {
			display:    true,
			text:       title,
			fontSize:   24,
			fontStyle:  'bold',
			fontFamily: 'Pretendard'
		};
	},
	// 공통 타이틀 옵션
	getTitleOptions: function(title) {
		return {
			display: true,
			text:    title,
			font:    {size:24, weight:'bold', family: 'Pretendard'}
		};
	},
	// 공통 서브타이틀 옵션
	getSubtitleOptions: function(subtitle) {
		return {
			display: true,
	        text:    subtitle,
			padding: {bottom: 10},
			font:    {size:20, weight:'bold', family: 'Pretendard'}
		};
	},
	// 공통 Legend 옵션
	getLegendOptions: function() {
		return {
			display: true,
	        position: 'top',
			labels: {
				font:    {size:16, weight:'normal', family: 'Pretendard'}
			}
		};
	},
};


(function(){
    /**
     *  Extensions of CanvasRenderingContext2D functions
     *  1. Override CanvasRenderingContext2D.filltext function with lineBreak(\n) support.
     *  2. Override CanvasRenderingContext2D.measureText function to multiLines support.
     *
     *  sanguneo on 16. 5. 10.
    if(CanvasRenderingContext2D.prototype.sanguneo) return;
    var orig_fillText = CanvasRenderingContext2D.prototype.fillText;
    CanvasRenderingContext2D.prototype.fillText = function(){
        if(typeof arguments[0] === 'number'){
            arguments[0] += '';
        }
        var fontSize = parseInt(this.font);
        var textSplit = arguments[0].split('\n');
        arguments[2] -= fontSize;
        for (var idx=0; idx< textSplit.length; idx++){
            arguments[0] = textSplit[idx];
            arguments[2] += fontSize;
            orig_fillText.apply(this, arguments);
        }
    }
    var orig_measureText = CanvasRenderingContext2D.prototype.measureText;
    CanvasRenderingContext2D.prototype.measureText = function(){
        if(typeof arguments[0] === 'number'){
            arguments[0] += '';
        }
        var textSplit = arguments[0].split('\n');
        var textMax = '';
        for (var idx=0; idx< textSplit.length; idx++){
            textMax = textSplit[idx].length > textMax.length ? textSplit[idx] : textMax;
        }
        arguments[0] = textMax;
        return orig_measureText.apply(this, arguments); // return longest text width
    }

    CanvasRenderingContext2D.prototype.sanguneo = true;
     */
})();
