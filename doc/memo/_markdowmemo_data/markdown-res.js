(function () {
//markdown-itの設定
var markdownit = window.markdownit({html:true,xhtmlOut:true,linkify: true,langPrefix:'code-style ',breaks: true});

//マークダウンを HTML に変換して再セット
var md = markdownit.render(getHtml("#memo_body"));
md = moveCodeStyle(md);
$("body").html(md);

// 比較演算子が &lt; 等になるので置換
function getHtml(selector) {
  var html = $(selector).html();
  html = html.replace(/&lt;/g, '<');
  html = html.replace(/&gt;/g, '>');
  html = html.replace(/&amp;/g, '&');
  return html;
}

// code-styleをpreタグに移動。
function moveCodeStyle(html) {
  return html.replace(/<pre><code class="code-style /g
      , '<pre class="code-style"><code class="');
}

}());
