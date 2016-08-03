var gulp = require('gulp'), htmlmim = require("gulp-htmlmin"), // html压缩
minifycss = require('gulp-minify-css'), // css压缩
// jshint = require('gulp-jshint'),//js校验
uglify = require('gulp-uglify'), // js压缩
imagemin = require('gulp-imagemin'), // 图片压缩
rename = require('gulp-rename'), // 文件更名
clean = require('gulp-clean'), // 删除文件
concat = require('gulp-concat'), // 文件合并
notify = require('gulp-notify'), // 信息提示
changed = require('gulp-changed'), // 变更过滤
plumber = require('gulp-plumber'), // 处理任务构建过程中的异常
less = require('gulp-less'), // css 编译工具
watch = require('gulp-watch');

var resRoot = "resources";
var deployRoot = "src/main/resources";
var paths = {
	htmls : [ resRoot + "/**/*.html" ],
	styles : [ resRoot + "/**/*.css" ],
	scripts : [ resRoot + "/**/*.js" ],
	images : [ resRoot + "/**/*.png" ]
};

gulp.task("html", function() {
	var options = {
		removeComments : true,// 清除HTML注释
		collapseWhitespace : true,// 压缩HTML
		collapseBooleanAttributes : true,// 省略布尔属性的值 <input checked="true"/>
											// ==> <input />
		removeEmptyAttributes : true,// 删除所有空格作属性值 <input id="" /> ==> <input
										// />
		removeScriptTypeAttributes : true,// 删除<script>的type="text/javascript"
		removeStyleLinkTypeAttributes : true,// 删除<style>和<link>的type="text/css"
		minifyJS : true,// 压缩页面JS
		minifyCSS : true
	// 压缩页面CSS
	};
	gulp.src(paths.htmls).pipe(changed(deployRoot)).pipe(plumber()).pipe(htmlmim(options)).pipe(gulp.dest(deployRoot)).pipe(notify({
		message : "html task ok"
	}));
});

gulp.task("style", function() {
	gulp.src(paths.styles).pipe(changed(deployRoot))// 过滤变更
	.pipe(plumber())// 屏蔽异常
	.pipe(less())// less编译
	.pipe(minifycss())// 压缩css
	.pipe(gulp.dest(deployRoot)).pipe(notify({
		message : "style task ok"
	}));
});

gulp.task("script", function() {
	gulp.src(paths.scripts).pipe(changed(deployRoot)).pipe(plumber()).pipe(uglify()).pipe(gulp.dest(deployRoot)).pipe(notify({
		message : " script task ok"
	}));
});

gulp.task('watch', function() {
	gulp.watch(paths.htmls, [ 'html' ]);
	gulp.watch(paths.styles, [ 'style' ]);
	gulp.watch(paths.scripts, [ 'script' ]);
});

// gulp.task('watch',function(){
// return gulp.watch(paths.scripts, function(event) {
// scriptFun(event.path);
// console.log('File ' + event.path + ' was ' + event.type + ', running
// tasks...');
// });
// });

gulp.task('build', [ 'html', 'style', 'script' ]);

gulp.task('default', [ 'watch' ]);