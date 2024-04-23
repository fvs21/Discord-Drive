<template>
  <h1 style="text-align: center;">Discord Drive</h1>
  <div>
    <div class="upload-file">
      <form @submit.prevent="uploadFile" enctype="multipart/form-data">
        <div class="fields">
          <label for="file-upload" class="custom-file-upload">Select file</label>
          <br>
          <input id="file-upload" type="file" @change="addedFile">
          <span>{{ fileName || errorMessage || successMessage }}</span>
        </div>
        <div class="fields">
          <button type="submit">Upload</button>
        </div>
      </form>
    </div>
    <div class="files-list">
      <h2>Files</h2>
      <div class="files-container">
        <div v-for="item in files" v-bind:key="item" class="files-item">
          <h3>{{ item.fileName }}</h3>
          <div class="fields">
            <span>Size: {{ readableFileSize(item.fileSize) }}</span>
          </div>
          <div>
            <button class="downloadButton" @click="downloadFile(item.fileName)">Download</button>
          </div>
          <div v-if="loading.loading && loading.name == item.fileName">
            <span>Downloading...</span>
          </div>
        </div>
      </div>
    </div>
  </div>
  
</template>

<script setup>
import { ref } from "vue";
import axios from 'axios';

const fileName = ref("");
const file = ref(null);
const errorMessage = ref("");
const files = ref({});
const successMessage = ref("");
const loading = ref({});

function addedFile(event) {
  fileName.value = event.target.files[0].name;
  file.value = event.target.files[0];
  console.log(file.value);
}

async function uploadFile() {
  if(file.value == null) {
    errorMessage.value = "File not selected";
    return;
  } else {
    errorMessage.value = "";
  }
  console.log(file.value)
  
  const formData = new FormData();
  formData.append("file", file.value);
  
  const response = await axios.post("http://localhost:8080/api/v1/files", formData);
  if(response.status == 201) {
    file.value = ref(null);
    fileName.value = "";
    successMessage.value = "File uploaded!";
    getFiles();
  }
}

async function getFiles() {
  const response = await axios.get("http://localhost:8080/api/v1/files");
  console.log(response);

  files.value = response.data;
}

async function downloadFile(fileName) {
  loading.value = {
    name: fileName,
    loading: true
  };
  const response = await axios({
    url: "http://localhost:8080/api/v1/files/" + fileName,
    method: 'GET',
    responseType: 'blob'
  });
  if(response.status == 200) {
    const href = URL.createObjectURL(response.data);
    const link = document.createElement('a');
    link.href = href;

    link.setAttribute('download', fileName);
    document.body.appendChild(link);
    link.click();

    document.body.removeChild(link);
    URL.revokeObjectURL(href);
    loading.value = {};
  }
}

function readableFileSize(size) {
    var units = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    var i = 0;
    while(size >= 1024) {
        size /= 1024;
        ++i;
    }
    return size.toFixed(1) + ' ' + units[i];
}

getFiles();

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
}

.upload-file {
  text-align: center;
}

.custom-file-upload {
  border: 1px solid #ccc;
  display: inline-block;
  padding: 15px 32px;
  cursor: pointer;
}
button[type="submit"] {
  background-color: #1442cd;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  border-radius: 6px;
  cursor: pointer;
}

.fields {
  padding: 20px;
}

.files-list {
  padding: 20px;
}

input[type="file"] {
  display: none;
}


.files-container {
  display: grid;
  grid-template-columns: repeat(5, minmax(0, 1fr));
  grid-auto-rows: 250px;
  gap: 30px;
}

.files-item {
  text-align: center;
  background-color: #ebedf0;
  border-radius: 6px;
  padding-top: 20px;
}
.downloadButton {
  background-color: #1442cd;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  border-radius: 6px;
  cursor: pointer;
}
</style>
