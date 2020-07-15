function getData() {
  fetch('/data').then(response => response.text()).then(data => {
    document.getElementById('fetch_info').innerText = data;
  });
}

/*
 * Functions to be called after
 * the window has loaded
 */
window.onload = () => {
  getData();
}