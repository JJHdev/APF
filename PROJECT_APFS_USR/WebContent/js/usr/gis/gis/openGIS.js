/**
*******************************************************************************
***    명칭: openGIS.js
***    설명: 투자서비스 - GIS 화면
***
***    -----------------------------    Modified Log   ------------------------
***    버전        수정일자        수정자        내용
*** ---------------------------------------------------------------------------
***    1.0      2023.04.04    LSH        First Coding.
*******************************************************************************
**/
var entSggHeatSource;
var entSggHeatLayer;
var nowEntBzentyNo;
var pointLayer;
var vWorldLayers = {};
var StaticChart;
var StaticChartConfig;
var map;
let draw;
let measureTooltipElement;
let measureTooltip;
let arrToolTip = [];
let helpTooltipElement;
let helpTooltip;
const drawSource = new ol.source.Vector();
const drawVector = new ol.layer.Vector({
	source: drawSource,
	name: "draw_map",
	style: new ol.style.Style({
		fill: new ol.style.Fill({
			color: 'rgba(255, 255, 255, 0.2)',
		}),
		stroke: new ol.style.Stroke({
			color: '#009fff',
			width: 3,
		}),
		image: new ol.style.Circle({
			radius: 7,
			fill: new ol.style.Fill({
				color: '#ffcc33',
			}),
		}),
	}),
});

$(function() {/*
	$.ajax({
		url: getUrl('/usr/gis/gis/getListLayerInfo.do'),
		params: {
			service: 'WFS',
			version: '1.0.0',
			reqType: 'GetFeature',
			layerNm: 'getListEnt',
			maxFeatures: 50,
			outputFormat: 'application/json',
		},
		success: function(result) {
			console.log(result);
		}
	});*/
	// 지도 표출
	small_floating();

	var container = document.getElementById('popup');
	var content = document.getElementById('popup-content');
	overlay = new ol.Overlay({
		element: container,
		autoPan: true,
		autoPanAnimation: {
			duration: 250,
		},
	});

	vWorldLayers['base'] = new ol.layer.Tile({
		title: 'VWorld Base Map',
		visible: true,
		type: 'base',
		source: new ol.source.XYZ({
			url: 'https://xdworld.vworld.kr/2d/Base/service/{z}/{x}/{y}.png',
			crossOrigin: 'anonymous'
		})
	});

	vWorldLayers['satellite'] = new ol.layer.Tile({
		title: 'VWorld Satellite Map',
		visible: true,
		type: 'base',
		source: new ol.source.XYZ({
			url: 'https://xdworld.vworld.kr/2d/Satellite/service/{z}/{x}/{y}.jpeg',
			crossOrigin: 'anonymous'
		})
	});

	proj4.defs("EPSG:3857", "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext +no_defs");
	ol.proj.setProj4 = proj4;

	map = new ol.Map({
		target: 'map',
		view: new ol.View({
			projection: 'EPSG:3857',
			center: new ol.geom.Point([126.9380517322744, 37.16792263658907]).transform('EPSG:4326', 'EPSG:3857').getCoordinates(), //포인트의 좌표를 리턴함,
			zoom: 14,
			minZoom: 7,
			maxZoom: 19,
		}),
		layers: [
			// 뷰 style 관리
			new ol.layer.Tile({
				source: new ol.source.XYZ({
					url: 'https://xdworld.vworld.kr/2d/Base/service/{z}/{x}/{y}.png',//default base map
					crossOrigin: 'anonymous'
				})
			}),
			drawVector],
		overlays: [overlay]
	});

	var hover = null;
	var container = document.getElementById('popup');
	var content = document.getElementById('popup-content');/*
	map.on('pointermove', function(evt) {
		map.getTargetElement().style.cursor = map.hasFeatureAtPixel(evt.pixel) ? 'pointer' : '';
		if (hover != null) {
			$('.ol-popup').css('display', 'none');
			hover = null;
		}  
		map.forEachFeatureAtPixel(evt.pixel, function(f) {
			hover = f;
			return true;
		});
		if(hover){
			const coordinate = evt.coordinate;
			var feature = map.forEachFeatureAtPixel(evt.pixel,
					function (feature) {
			        	return feature;
			});
			content.innerHTML = '<p class="ol-pop-text">'+feature.get('name')+'</p>';
			$('.ol-popup').css('display', '');
	    	overlay.setPosition(coordinate);
		}
	});*/

	$('#bizTypeCode').appComboBox({
		params: { upCdId: CODE.BIZ_RLM.code },
		prompt: '사업분야',
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
	});

	$('#investTypeCode').appComboBox({
		url: getUrl('/com/common/getInvtRlmComboCode.do'),
		prompt: '투자분야',
		loadFilter: function(data) {
			data.unshift(COMBO.INIT_ALL);
			return data;
		},
	});

	$('input[type=checkbox][name=aa]').change(function() {
		if (!!entSggHeatLayer) {
			if ($(this).is(':checked')) {
				entSggHeatLayer.setVisible(true);
			}
			else {
				entSggHeatLayer.setVisible(false);
			}
		}
		else {
			$.commMsg.alert("조회된 레이어가 없습니다.");
		}
	});

	// 스마트검색 폼
	let P_FORM = $('#searchForm');
	// 경영체 그리드 (app_gridutils.js)
	let P_GRID = $.gridUtils.appGisEntGrid({});
	// 스마트검색 컨트롤 (app_smartsearch.js)
	$('#appSmartSearch').appSmartSearch({
		// 모드: GIS
		mode: MODE.GIS,
		// 검색폼 객체
		form: P_FORM,
		// 그리드 객체
		grid: P_GRID,
		// 2023.08.14 LSH 체크박스 영역 CSS
		itemCls: 'box-lev-50p',
		// 2023.08.14 LSH 콜백 함수
		callbacks: {
			// 2023.08.14 LSH 초기화후 처리함수
			onClickReset: function() {
				//$('button.icon-times').trigger('click');
			},
			// 2023.08.14 LSH 검색후 처리함수
			onClickSearch: function() {
				$('button.icon-times').trigger('click');
			}
		},
		// 검색조건 항목
		items: [[{
			label: '투자분야',
			name: 'invtFldList',
			type: 'appSelectBox',
			// 초기화버튼 표시여부 
			initicon: true,
			info: {
				click: function() {
					// 투자분야선택 팝업오픈(app_popup.js)
					popup.openInvestField({
						// 검색버튼 클릭
						onClickSearch: function(values) {
							$.formUtil.setValue(P_FORM, "invtFldList", values);
							P_GRID.doSearch();
						}
					});

				}
			},
			inputOptions: {
				form: 'checkbox',
				name: 'invtFldList',
				colCls: 'col',
				url: getUrl('/com/common/getInvtRlmComboCode.do'),
				// 첫번쨰값 선택처리
				selectIndex: '0',
				init: { code: '', text: '전체', click: bindCheckAll }
			}
		}], [{
			label: '사업분야',
			name: 'bizFldList',
			type: 'appSelectBox',
			// 초기화버튼 표시여부 
			initicon: true,
			info: {
				click: function(values) {
					// 사업분야 팝업오픈(app_popup.js)
					popup.openBizField({
						// 검색버튼 클릭
						onClickSearch: function(values) {
							$.formUtil.setValue(P_FORM, "bizFldList", values);
							P_GRID.doSearch();
						}
					});
				}
			},
			inputOptions: {
				form: 'checkbox',
				name: 'bizFldList',
				colCls: 'col',
				// 첫번쨰값 선택처리
				selectIndex: '0',
				url: getUrl('/com/common/getComboCode.do'),
				params: { upCdId: CODE.BIZ_RLM.code },
				init: { code: '', text: '전체', click: bindCheckAll }
			}
		}], [{
			label: '투자희망금액',
			name: 'invtHopeCd',
			type: 'appSlideBox',
			// 초기화버튼 표시여부 
			initicon: true,
			inputOptions: {
				input: { id: 'invtHopeCd', name: 'invtHopeCd', value: '' },
				params: { upCdId: CODE.FUND_SIZE.code }
			}
		}], [{
			label: '지역 (시도)',
			name: 'lctnCd',
			type: 'appComboBox',
			// 초기화버튼 표시여부 
			initicon: true,
			inputOptions: {
				name: 'lctnCd',
				init: COMBO.INIT_ALL,
				params: { upCdId: CODE.CTPV_SE.code }
			}
		}]]
	});

	
});

function baseChange(data) {
	if (data == "satellite") {
		$('.btnSatellite').addClass('active');
		$('.btnBase').removeClass('active');
	}
	else {
		$('.btnSatellite').removeClass('active');
		$('.btnBase').addClass('active');
	}

	var layer = vWorldLayers[data];
	if (layer) {
		layer.setOpacity(1);
		updateRenderEdgesOnLayer(layer);
		map.getLayers().setAt(0, layer);
	}
}

var updateRenderEdgesOnLayer = function(layer) {
	if (layer instanceof ol.layer.Tile) {
		var source = layer.getSource();
	}
}

function detail_gisPop_show(row) {
	let src = false;
	let bno = row['bzentyNo'];

	if (row['rprsFileSn']) { // 대표이미지
		src = getUrl('/usr/file/linkEntFile.do?sn='+row['rprsFileSn']);
	} else { // 임시이미지
		src = getUrl('/images/app/ENT_IMAGE'+(bno%10)+'.jpg');
	}
	$('#entImgBox').attr('src', src);
	
	$('.leftdetailbox').addClass('show');
	gridPopInfo(row);
	showPoint(row.lat, row.lng, row.bzentyNm);
	setTimeout(function() {
		$('.leftdetailbox').css('z-index', '1')
	}, 500);
};

function gridPopInfo(row) { //경영체 팝업 GRID
	nowEntBzentyNo = "";
	nowEntBzentyNo = row.bzentyNo;
	
	// 2023.09.12 LSH 경영체이미지 교체처리
	$('img.app-ent-listimage').remove();
	$('.leftdetailbox').prepend($.bizUtils.getEntImage(row, MODE.LIST));
	
	$(".bizListBox").empty();
	if (!!row.bizFldText) {//사업분야
		var bizFldTextArr = row.bizFldText.split(',');
		for (var i = 0; i < bizFldTextArr.length; i++) {//사업분야
			var bizFldTextEl = '<span class="bg-mint-t10 text-mint mb-1">' + bizFldTextArr[i] + '</span>';
			$(".bizListBox").append(bizFldTextEl);
		}
	}
	if (!!row.invtFldText) {//투자분야
		var invtFldTextArr = row.invtFldText.split(',');
		for (var i = 0; i < invtFldTextArr.length; i++) {
			var invtFldTextEl = '<span class="bg-lavendar-t10 text-lavendar mb-1">' + invtFldTextArr[i] + '</span>';
			$(".bizListBox").append(invtFldTextEl);
		}
	}
	$('.bzentyNm').text(nullToDefaultString(row.bzentyNm));
	$('.brno').text(nullToDefaultString($.formatUtil.toBizNo(row.brno)));
	$('.tpbizNm').text(nullToDefaultString(row.tpbizNm));
	$('.fndnYmd').text(nullToDefaultString($.formatUtil.toKorDate(row.fndnYmd)));
	$('.lctnAddr').text(nullToDefaultString(row.lctnAddr) + ' ' + nullToDefaultString(row.lctnDaddr));
	$('.hmpgAddr').text(nullToDefaultString(row.hmpgAddr));
	$('.rprsTelno').text(nullToDefaultString($.formatUtil.toPhone(row.rprsTelno)) + ' / ' + nullToDefaultString($.formatUtil.toPhone(row.fxno)));
	$('.mainBizCn').text(nullToDefaultString(row.mainBizCn));
	$('.coreItmCn').text(nullToDefaultString(row.coreItmCn));
	$('.picNm').text(nullToDefaultString(row.picNm));
	$('.picTelno').text(nullToDefaultString(row.picTelno));
}

function findAddr() {
	var endAddr = $('.lctnAddr').text();
	var stX = "";
	var stY = "";
	var geocoder = new kakao.maps.services.Geocoder();

	if (endAddr.indexOf('정보없음') == -1 && !!geocoder) {
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch(endAddr, function(result, status) {
			// 정상적으로 검색이 완료됐으면 
			if (status === kakao.maps.services.Status.OK) {
				var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

				//시작지
				navigator.geolocation.getCurrentPosition(function(pos) {
					slat = pos.coords.latitude;
					slng = pos.coords.longitude;
					var stCoords = new kakao.maps.LatLng(slat, slng);
					kakaoStCoords = stCoords.toCoords().toString();
					var realStCoords = kakaoStCoords.split(',');

					stX = realStCoords[0].replace('(', '');
					stY = realStCoords[1].replace(')', '');

					// 접속한 디바이스 환경
					if (/Mobi|Android/i.test(navigator.userAgent)) {
						window.open('https://map.kakao.com/?from=roughmap&sName=' + encodeURI('현재위치') + '&sX=' + stX + '&sY=' + stY.replace(/ /g, "") + '&eName=' + encodeURI(endAddr));
					}
					else {
						window.open('https://map.kakao.com/?from=roughmap&sName=' + encodeURI('현재위치') + '&sX=' + stX + '&sY=' + stY.replace(/ /g, "") + '&eName=' + encodeURI(endAddr));
					}
				});
			}
		});
	}
	else {
		$.commMsg.alert("경영체에 대한 위치정보가 존재하지 않아 길찾기가 불가능합니다.")
	}
}

function showPoint(lat, lng, bzName) {
	if (!!lat && !!lng) {
		if (!!pointLayer) {
			map.removeLayer(pointLayer);
		}
		var featureSource = new ol.source.Vector({});
		pointLayer = new ol.layer.Vector({
			source: featureSource
		});

		var pointFeature = new ol.Feature({
			geometry: new ol.geom.Point([parseFloat(lng), parseFloat(lat)]).transform('EPSG:4326', 'EPSG:3857'),
			name: bzName,
			population: 4000,
			rainfall: 500
		});

		var iconStyle = new ol.style.Style({
			image: new ol.style.Icon(({
				color: 'rgba(255, 0, 0, .5)',
				crossOrigin: 'anonymous',
				src: getUrl('/images/sub/opengis_marker.svg'),
				scale: 0.7,
				opacity: 0.6,
			}))
		});
		pointFeature.setStyle(iconStyle);

		featureSource.addFeature(pointFeature);
		map.addLayer(pointLayer);
		map.getView().setCenter(new ol.geom.Point([parseFloat(lng), parseFloat(lat)]).transform('EPSG:4326', 'EPSG:3857').getCoordinates());
	}
	else {
		if (!!pointLayer) {
			map.removeLayer(pointLayer);
		}
		$.commMsg.alert('경영체에 대한 위치정보가 존재하지 않습니다.');
	}
}

function currentLocation() {
	navigator.geolocation.getCurrentPosition(function(pos) {
		slat = pos.coords.latitude;
		slng = pos.coords.longitude;
		map.getView().setCenter(new ol.geom.Point([parseFloat(slng), parseFloat(slat)]).transform('EPSG:4326', 'EPSG:3857').getCoordinates());
	});
}

function showViewEntInfo() {
	var params = { bzentyNo: nowEntBzentyNo, }
	// 상세보기
	$.formUtil.submitForm(getUrl('/usr/invest/ent/viewEnt.do'), {
		params: params
	});
};

function getEntSggLayer(code, selectType) {

	var layerCount;
	var endCount = 0;
	var data = new Object();

	if (selectType == "mgmt") {
		var layerName = "getEntSgg";
	}
	else {
		var layerName = "getEntAvgSgg";
	}
	for(key in code) {
		data[key] = code[key];
	}

	/*data['fldCd'] = code['bizTypeCode'];*/
		
	map.removeLayer(entSggHeatLayer);
	document.getElementById('divLoading').style.display = '';

	entSggHeatSource = new ol.source.TileWMS({
		url: getUrl('/usr/gis/gis/gisProxy.do'),
		params: {
			'layers': layerName,
			'viewparams': genViewParams(data),
			'TILED': true
		},
		serverType: 'geoserver',
		transition: 0,
		projection: 'EPSG:3857',
		tileLoadFunction: function(tile, src) {
			tile.getImage().src = src;
			layerCount = entSggHeatLayer.N.source.a.i;
		}
	});

	entSggHeatLayer = new ol.layer.Tile({
		source: entSggHeatSource,
		serverType: 'geoserver',
		transition: 0,
		projection: 'EPSG:3857',
	});

	map.addLayer(entSggHeatLayer);
	entSggHeatLayer.setZIndex(10);

	$("input:checkbox[id='a1']").prop("checked", true);

	entSggHeatSource.on('tileloadend', function(event) {
		if (++endCount >= layerCount) {
			document.getElementById('divLoading').style.display = 'none';
		}
	});
}

function getGisData() {
	var investTypeCode = document.querySelector('#investTypeCode > option:checked').value;
	var bizTypeCode = document.querySelector('#bizTypeCode > option:checked').value;
	var currentType = document.querySelector('#currentType > option:checked').value;

	if (!investTypeCode && !bizTypeCode) {
		if (!currentType) {
			$.commMsg.alert('현황을 선택해주세요.');
		}
		else {
			//getEntSggLayer(null, currentType);//전체조회
			$.commMsg.alert('최소 한가지의 분야를 선택해주세요.');
		}
	}
	else if (!!investTypeCode && !!bizTypeCode) {
		//$.commMsg.alert('투자분야와 사업분야중 한가지 항목만 선택해주세요.');
		grpGrid({bizTypeCode: bizTypeCode,investTypeCode:investTypeCode}, currentType);
		getEntSggLayer({bizTypeCode: bizTypeCode,investTypeCode:investTypeCode}, currentType);
		$('#myChart').css('display', 'block');
		$('.noChartDiv').css('display', 'none');
	}
	else {
		if (!currentType) {
			$.commMsg.alert("현황을 선택해주세요.");
		}
		else {
			if (!investTypeCode) {
				grpGrid({bizTypeCode:bizTypeCode,investTypeCode:''}, currentType);
				getEntSggLayer({bizTypeCode: bizTypeCode,investTypeCode:''}, currentType);//사업분야
				$('#myChart').css('display', 'block');
				$('.noChartDiv').css('display', 'none');
			}
			else {
				grpGrid({bizTypeCode: '',investTypeCode:investTypeCode}, currentType);
				getEntSggLayer({bizTypeCode: '',investTypeCode:investTypeCode}, currentType);//투자분야
				$('#myChart').css('display', 'block');
				$('.noChartDiv').css('display', 'none');
			}
		}
	}
}

function grpGrid(fldCd, chartType) {
	var data = {
		chartFlag: chartType
	};
	
	for(key in fldCd) {
		data[key] = fldCd[key];
	}
	console.log(data);
	var sggNmArr = [];
	var valArr = [];

	$.ajax({
		url: getUrl('/usr/gis/gis/ajax/getChartData.do'),
		dataType: 'json',
		data: data,
		contentType: "application/json; charset=utf-8",
		async: false,
		type: 'GET',
		success: function(data) {
			console.log(data);
			if (chartType == "mgmt") {
				for (var i = 0; i < data.chartDataList.length; i++) {
					sggNmArr.push(data.chartDataList[i].sggNm);
					valArr.push(data.chartDataList[i].mgmtsum);
				}
				chartLoad(sggNmArr, valArr, "경영체 현황");
			}
			else {
				for (var i = 0; i < data.chartDataList.length; i++) {
					sggNmArr.push(data.chartDataList[i].sggNm);
					valArr.push(data.chartDataList[i].avgFnnrAmt);
				}
				chartLoad(sggNmArr, valArr, "자본규모 현황");
			}
		},
		error: function(request, status, error) {
			console.log("데이터 조회 오류\ncode:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		}
	});
}

function chartLoad(sggNmArr, valArr, label) {
	var canvas = document.getElementById('myChart');
	var width = calculateCanvasWidth(sggNmArr);
	canvas.width = width;
	canvas.style.width = width+'px';
	
	if(StaticChart != undefined) //이미 사용중이면 destroy
		StaticChart.destroy();
	
	const ctx = document.getElementById('myChart').getContext('2d');
	const config = {
		type: 'bar',
		data: {
			label: '미선택',
			data: [0],
			backgroundColor: 'rgba(255, 99, 132, 0.2)',
			borderColor: 'rgb(255, 99, 132)',
		},
		options: {
			offset: false,
			scales: {
				x: {
		            ticks: {
		                autoSkip: false,
		            },
		        },
				y: {
					beginAtZero: true,
				}
			},
			responsive: false,
			maintainAspectRatio: false,
			plugins: {
				tooltip: {
			      mode: 'nearest',
			      intersect: false
			    },
			},
			hover: {
				mode: 'index',
				intersect: false
			},
		}
	}
	var myChart = new Chart(ctx, config);
	StaticChartConfig = config;
	StaticChart = myChart;
	
	StaticChartConfig.data.datasets = [];
	var sggDataset = {
		label: label,
		data: valArr,
		backgroundColor: 'rgba(255, 99, 132, 0.2)',
		borderColor: 'rgb(255, 99, 132)',
	}
	
	StaticChartConfig.data.labels = sggNmArr;
	StaticChartConfig.data.datasets.push(sggDataset);
	StaticChart.update();
}

function calculateCanvasWidth(data) {
	const calculatedWidth = data.length * 70;
	const minCanvasWidth = 890; 

	return Math.max(minCanvasWidth, calculatedWidth);
}

function textSearch() {
	let P_GRID = $.gridUtils.appGisEntGrid({});
	var srchText = document.getElementById('srchText').value;
	P_GRID.doSearch({ srchText: srchText });
}

function handleZoomInClick() {
	const zoom = map.getView().getZoom() + 1;
	map.getView().setZoom(zoom);
};

function handleZoomOutClick() {
	var zoom = map.getView().getZoom() - 1;
	map.getView().setZoom(zoom);
};

function nullToDefaultString(s) {
	var text = s;
	if (!s) {
		text = '정보없음';
	}
	text = text.toString();
	return text;
}

function toPhone(v, o) {
	let a = [$.formatUtil.toPhone(v)];
	if (o['fxno'])
		a.push($.formatUtil.toPhone(o['fxno']));
	return a.join(' / ');
}


function genViewParams(params) {
	var cln = ":";
	var scln = ";";
	var viewParams = "";
	var i = 0
	for (var p in params) {
		if (i > 0) {
			viewParams += scln + p + cln + params[p];
		} else {
			viewParams += p + cln + params[p];
		}
		i++;
	}
	return viewParams;
}

let interval = 0;
function addInteraction(gridType) {
	console.log('클릭됨!');
	drawStop();
	const type = gridType;
	if (type == 'LineString') {
		$(".LineString").addClass("active");
	} else {
		$(".Polygon").addClass("active");
	}
	draw = new ol.interaction.Draw({
		source: drawSource,
		type: type,
		style: new ol.style.Style({
			fill: new ol.style.Fill({
				color: 'rgba(255, 255, 255, 0.2)',
			}),
			stroke: new ol.style.Stroke({
				color: 'rgba(0, 0, 0, 0.5)',
				lineDash: [10, 10],
				width: 2,
			}),
			image: new ol.style.Circle({
				radius: 5,
				stroke: new ol.style.Stroke({
					color: 'rgba(0, 0, 0, 0.7)',
				}),
				fill: new ol.style.Fill({
					color: 'rgba(255, 255, 255, 0.2)',
				}),
			}),
		}),
	});
	map.addInteraction(draw);

	createMeasureTooltip();
	createHelpTooltip();

	let listener;
	draw.on('drawstart', function(evt) {
		
		// set sketch
		sketch = evt.feature;

		/** @type {import("../src/ol/coordinate.js").Coordinate|undefined} */
		let tooltipCoord = evt.coordinate;

		listener = sketch.getGeometry().on('change', function(evt) {
			const geom = evt.target;
			let output;
			var cont = "";

			if (type == 'LineString') {
				output = formatLength(geom);
			} else {
				output = formatArea(geom);
			}
			tooltipCoord = geom.getLastCoordinate();
			cont += '<div><span class="ol-tooltip-value">'.concat(
				output,
				'</span> <span class="ol-tooltip-del">지우기</span> </div>'
			);
			measureTooltipElement.innerHTML = cont;
			measureTooltip.setPosition(tooltipCoord);
		});
	});

	draw.on('drawend', function() {
		drawNo = "draw_"+interval;
				measureTooltipElement.className = "ol-tooltip ol-tooltip-static ".concat(
		  drawNo
		);
		/*measureTooltipElement.className = 'ol-tooltip ol-tooltip-static';*/
		measureTooltipElement.setAttribute("data-draw-no",drawNo);
		arrToolTip.push(drawNo);
		interval += 1;
		measureTooltip.setOffset([0, -7]);
		measureTooltipElement.querySelector(".ol-tooltip-del").addEventListener("click", function(e){
			const delNum = e.target.parentNode.parentNode.dataset.drawNo;
			var drawFeatures = drawSource.getFeatures();
			var delFeature = drawFeatures[arrToolTip.indexOf(delNum)];
			drawSource.removeFeature(delFeature);
			arrToolTip.splice(arrToolTip.indexOf(delNum), 1);
			e.target.parentNode.parentNode.remove();
		});
		// unset sketch
		sketch = null;
		// unset tooltip so that a new one can be created
		measureTooltipElement = null;
		createMeasureTooltip();
		ol.Observable.unByKey(listener);
		drawStop();
	});
}

/**
 * Creates a new help tooltip
 */
function createHelpTooltip() {
	if (helpTooltipElement) {
		helpTooltipElement.parentNode.removeChild(helpTooltipElement);
	}
	helpTooltipElement = document.createElement('div');
	helpTooltipElement.className = 'ol-tooltip hidden';
	helpTooltip = new ol.Overlay({
		element: helpTooltipElement,
		offset: [15, 0],
		positioning: 'center-left',
	});
	map.addOverlay(helpTooltip);
}

/**
 * Creates a new measure tooltip
 */
function createMeasureTooltip() {
	if (measureTooltipElement) {
		measureTooltipElement.parentNode.removeChild(measureTooltipElement);
	}
	measureTooltipElement = document.createElement('div');
	measureTooltipElement.className = 'ol-tooltip ol-tooltip-measure';
	measureTooltip = new ol.Overlay({
		element: measureTooltipElement,
		offset: [0, -15],
		positioning: 'bottom-center',
		stopEvent: false,
		insertFirst: false,
	});
	map.addOverlay(measureTooltip);
}

const formatLength = function(line) {
	const length = ol.Sphere.getLength(line);
	let output;
	if (length > 100) {
		output = Math.round((length / 1000) * 100) / 100 + ' ' + 'km';
	} else {
		output = Math.round(length * 100) / 100 + ' ' + 'm';
	}
	return output;
};

function formatArea(polygon) {
	var area = ol.Sphere.getArea(polygon);
	var output;
	if (area > 10000) {
		output = Math.round((area / 1000000) * 100) / 100 + ' ' + 'km<sup>2</sup>';
	} else {
		output = Math.round(area * 100) / 100 + ' ' + 'm<sup>2</sup>';
	}
	return output;
};

function drawStop() {
	$(".LineString").removeClass("active");
	$(".Polygon").removeClass("active");
	map.removeInteraction(draw);
}

