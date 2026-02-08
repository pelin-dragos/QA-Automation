/**
 * Renders Playwright project cards from window.PLAYWRIGHT_PROJECTS.
 * Details open in a modal popup. Data lives in data/playwright-projects.js.
 */
(function () {
  var GITHUB_BASE = "https://github.com/pelin-dragos/QA-Automation/tree/main/Playwright/PROJECTS/";

  function escapeHtml(text) {
    if (!text) return "";
    var div = document.createElement("div");
    div.textContent = text;
    return div.innerHTML;
  }

  function createDetailsModal() {
    var modal = document.createElement("div");
    modal.id = "details-modal";
    modal.className = "details-modal";
    modal.setAttribute("role", "dialog");
    modal.setAttribute("aria-modal", "true");
    modal.setAttribute("aria-labelledby", "details-modal-title");
    modal.setAttribute("aria-hidden", "true");
    modal.innerHTML =
      "<div class=\"details-modal-backdrop\" aria-hidden=\"true\"></div>" +
      "<div class=\"details-modal-panel\">" +
        "<div class=\"details-modal-header\">" +
          "<h3 id=\"details-modal-title\" class=\"details-modal-title\"></h3>" +
          "<button type=\"button\" class=\"details-modal-close\" aria-label=\"Close\">&times;</button>" +
        "</div>" +
        "<p id=\"details-modal-body\" class=\"details-modal-body\"></p>" +
      "</div>";
    document.body.appendChild(modal);
    return modal;
  }

  function openDetailsModal(modal, title, body) {
    var titleEl = document.getElementById("details-modal-title");
    var bodyEl = document.getElementById("details-modal-body");
    if (titleEl) titleEl.textContent = title;
    if (bodyEl) bodyEl.textContent = body || "";
    modal.setAttribute("aria-hidden", "false");
    modal.classList.add("is-open");
    document.body.style.overflow = "hidden";
    var closeBtn = modal.querySelector(".details-modal-close");
    if (closeBtn) closeBtn.focus();
  }

  function closeDetailsModal(modal) {
    modal.setAttribute("aria-hidden", "true");
    modal.classList.remove("is-open");
    document.body.style.overflow = "";
  }

  function buildCard(project, detailsModal) {
    var li = document.createElement("li");
    li.className = "project-card";
    li.id = "project-" + project.id;

    var codeUrl = GITHUB_BASE + encodeURIComponent(project.folder);

    var header = document.createElement("div");
    header.className = "project-card-header";
    header.innerHTML =
      "<span class=\"project-card-number\">" + escapeHtml(project.number) + "</span>" +
      "<h3 class=\"project-card-title\">" + escapeHtml(project.title) + "</h3>" +
      "<p class=\"project-card-subtitle\">" + escapeHtml(project.subtitle) + "</p>";
    li.appendChild(header);

    var desc = document.createElement("p");
    desc.className = "project-card-description";
    desc.textContent = project.description || "";
    li.appendChild(desc);

    var actions = document.createElement("div");
    actions.className = "project-card-actions";
    actions.innerHTML =
      "<a href=\"" + escapeHtml(codeUrl) + "\" class=\"btn btn-primary\" target=\"_blank\" rel=\"noopener\">View code</a>";
    if (project.explanation) {
      var btnDetails = document.createElement("button");
      btnDetails.type = "button";
      btnDetails.className = "btn btn-secondary btn-details";
      btnDetails.setAttribute("aria-haspopup", "dialog");
      btnDetails.innerHTML = "Details <span class=\"icon\" aria-hidden=\"true\">&#9660;</span>";
      btnDetails.addEventListener("click", function () {
        openDetailsModal(detailsModal, project.title, project.explanation);
      });
      actions.appendChild(btnDetails);
    }
    li.appendChild(actions);

    return li;
  }

  function init() {
    var container = document.getElementById("playwright-project-cards");
    var data = window.PLAYWRIGHT_PROJECTS;
    if (!container || !Array.isArray(data)) return;

    var modal = createDetailsModal();

    var closeModal = function () { closeDetailsModal(modal); };

    modal.querySelector(".details-modal-backdrop").addEventListener("click", closeModal);
    modal.querySelector(".details-modal-close").addEventListener("click", closeModal);

    document.addEventListener("keydown", function (e) {
      if (e.key === "Escape" && modal.classList.contains("is-open")) closeModal();
    });

    var list = document.createElement("ul");
    list.className = "project-cards";
    data.forEach(function (project) {
      list.appendChild(buildCard(project, modal));
    });
    container.appendChild(list);
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", init);
  } else {
    init();
  }
})();
