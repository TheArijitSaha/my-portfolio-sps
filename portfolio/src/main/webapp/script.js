/* Constants */
/* Class names */
const commentDivClassName = 'commentDiv';
const commentUnorderedListClassName = 'commentList';
const commentListItemClassName = 'commentItem';
const commentSubmitButtonClassName = 'commentSubmit';
const commentTextInputClassName = 'commentInput';
/* URLs */
const commentsURL = 'comments';

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
 * Function to load comment form if user is signed in
 * else load sign in form
 */
function loadCommentForm() {
  fetch('/user').then(response => response.json()).then(user => {
    if (user.isLoggedIn) {
      addCommentFormToDOM(user);
    } else {
      addSignInFormToDOM(user);
    }
  });
}

/*
 * Function to add comment form
 */
function addCommentFormToDOM(user) {
  if (!user.isLoggedIn) return;

  /* Make Text Area Element */
  let commentTextInput = document.createElement('textarea');
  commentTextInput.className = commentTextInputClassName;
  commentTextInput.name = 'comment';
  commentTextInput.placeholder = 'Penny for your thoughts';

  /* Make Submit Button  */
  let commentSubmitButton = document.createElement('input');
  commentSubmitButton.className = commentSubmitButtonClassName;
  commentSubmitButton.type = 'submit';
  commentSubmitButton.value = 'Comment';

  /* Make Comment Form */
  let commentForm = document.createElement('form');
  commentForm.method = 'POST';
  commentForm.action = commentsURL;
  commentForm.appendChild(commentTextInput);
  commentForm.appendChild(commentSubmitButton);

  /* Append form to comment div */
  document.querySelector('div.' + commentDivClassName)
          .appendChild(commentForm);
}

/*
 * Functions to be called after
 * the window has loaded
 */
window.onload = () => {
  getComments();
  loadCommentForm();
}
