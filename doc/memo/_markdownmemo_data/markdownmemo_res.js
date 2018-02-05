(function () {

//メモ内容を取得.
var html = $('#memo_body').val();
//console.log(html);

//マークダウンを HTML に変換して再セット.
var markdownit = window.markdownit(
    {html:true,xhtmlOut:true,breaks:true,langPrefix:'language-',linkify:true,typographer:true});
var md = markdownit.render(html);
md = setCodeStyle(md);
$('body').html(md);

//-- 編集処理 --//
//code-styleをpreタグに設定.
function setCodeStyle(html) {
  //ファイル名有
  html = html.replace(/<pre><code class="language-(.+):(.+)">/g
      , '<div class="code-title">[ $2 ]</div><pre class="code-style"><code class="code-style language-$1" title="$2">');
  //ファイル名無 
  html = html.replace(/<pre><code class="language-(.+)">/g
      , '<pre class="code-style"><code class="code-style language-$1">');
  return html;
}

}());