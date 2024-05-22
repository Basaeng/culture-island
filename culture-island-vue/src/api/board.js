import { Axios } from "@/util/http-common";

const http = Axios();

function listArticle(param, success, fail) {
  http.get(`/board`, { params: param }).then(success).catch(fail);
  console.log("list");
}

function detailArticle(articleno, success, fail) {
  http.get(`/board/${articleno}`).then(success).catch(fail);
  console.log("detail");
}

function registArticle(article, success, fail) {
  // console.log("boardjs article", article);
  http.post(`/board`, JSON.stringify(article)).then(success).catch(fail);
  console.log("regist");
}

function getModifyArticle(articleno, success, fail) {
  // http.get(`/board/modify/${articleno}`).then(success).catch(fail);
  console.log("getmodify");
}

function modifyArticle(article, success, fail) {
  http.put(`/board`, JSON.stringify(article)).then(success).catch(fail);
  console.log("modify");
}

function deleteArticle(articleno, success, fail) {
  http.delete(`/board/${articleno}`).then(success).catch(fail);
  console.log("delete");
}

function uploadImage(formData) {
  return Axios.post("/board/uploadImage", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  });
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
  uploadImage,
};
