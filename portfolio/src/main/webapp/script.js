/* Constants */
const commentDivClassName = 'commentDiv';
const commentUnorderedListClassName = 'commentList';
const commentListItemClassName = 'commentItem';

/*
 * Function to fetch comments from the servlet
 */
function getComments() {
  fetch('/comments').then(response => response.json()).then(comments => {
    addCommentsToDOM(comments);
  });
}

/*
 * Function to add comment elements to the DOM
 */
function addCommentsToDOM(comments) {
  let listHTML = '';
  for (comment of comments) {
    listHTML += '<li class=\"' + commentListItemClassName
                + '\">' + comment + '</li>';
  }
  document.querySelector('ul.' + commentUnorderedListClassName)
          .innerHTML = listHTML;
}

/*
 * Functions to be called after
 * the window has loaded
 */
window.onload = () => {
  getComments();
}
