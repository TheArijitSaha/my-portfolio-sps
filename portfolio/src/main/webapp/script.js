/* Constants */
/* Class names */
const commentDivClassName = 'commentDiv';
const commentListDivClassName = 'commentListDiv';
const commentItemDivClassName = 'commentItemDiv';
const commentTextElementClassName = 'text';
const commentUserEmailElementClassName = 'userEmail';
const commentSubmitButtonClassName = 'commentSubmit';
const commentTextInputClassName = 'commentInput';
const loginDivClassName = 'loginDiv';
const loginDisclaimerParaClassName = 'loginDisclaimer';
const loginButtonClassName = 'loginButton';
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
  let commentList = document.querySelector('div.' + commentListDivClassName);
  
  for (comment of comments) {
    /* Make Text Element */
    let commentTextElement = document.createElement('p');
    commentTextElement.className = commentTextElementClassName;
    commentTextElement.innerText = comment.text;

    /* Make User Email Element  */
    let commentUserEmailElement = document.createElement('p');
    commentUserEmailElement.className = commentUserEmailElementClassName;
    commentUserEmailElement.innerText = comment.userEmail;

    /* Make Comment Item Div */
    let commentItem = document.createElement('div');
    commentItem.className = commentItemDivClassName;
    commentItem.appendChild(commentUserEmailElement);
    commentItem.appendChild(commentTextElement);

    /* Append to Comment List */
    commentList.appendChild(commentItem);
  }
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
      addSignInLinkToDOM(user);
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
 * Function to add sign in link
 */
function addSignInLinkToDOM(user) {
  if (user.isLoggedIn) return;

  /* Make login Disclaimer p element */
  let loginDisclaimer = document.createElement('p');
  loginDisclaimer.className = loginDisclaimerParaClassName;
  loginDisclaimer.innerText = 'Sign in to post comments';

  /* Make Login Button  */
  let loginButton = document.createElement('button');
  loginButton.className = loginButtonClassName;
  loginButton.innerHTML = '<a href=\"' + user.loginUrl + '\">Login</a>';

  /* Wrap in a div element */
  let loginDiv = document.createElement('div');
  loginDiv.className = loginDivClassName;
  loginDiv.appendChild(loginDisclaimer);
  loginDiv.appendChild(loginButton);

  /* Append form to comment div */
  document.querySelector('div.' + commentDivClassName)
          .appendChild(loginDiv);
}

/*
 * Functions to be called after
 * the window has loaded
 */
window.onload = () => {
  getComments();
  loadCommentForm();
}
