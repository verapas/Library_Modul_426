const burger = document.querySelector(".burger");
const offScreenMenu = document.querySelector(".unsichtbar");

burger.addEventListener("click", () => {
    burger.classList.toggle("active");
    offScreenMenu.classList.toggle("active");
});
