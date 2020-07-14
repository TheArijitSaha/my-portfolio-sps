function getData() {
  fetch('/data').then(response => response.text()).then(data => {
    document.getElementById('fetch_info').innerText = data;
  });
}
