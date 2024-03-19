function updateQuantity(card, buttonSize, buttonColor) {
	const selectedProd = card.querySelector('.productDetails');
	if (selectedProd != null) {
		const BtnQuantity = card.querySelector('.buttons-quantity');
		let index = selectedProd.value.indexOf('%');
		const values = selectedProd.value.substring(index + 1);
		const quantity = card.querySelector('.quantity');
		const prodValues = values.split('$');
		const resultArray = [];
		prodValues.forEach((val) => {
			const eachProdValues = val.split('&');
			resultArray.push(eachProdValues);
		});
		if (buttonSize.value !== "0" && buttonColor[0].value !== "") {
			let flag = false;
			resultArray.forEach((val) => {
				if (val[0] === buttonSize.value && val[1] === buttonColor[0].value) {
					quantity.innerText = val[2];
					flag = true;
					quantity.style.display = "block";
					BtnQuantity.value = quantity.innerText;
				}
			});
			if (flag === false) {
				quantity.innerText = "0";
				BtnQuantity.value = "1";
			}
		}

	}
}

const cards = document.querySelectorAll('.card');

cards.forEach((card) => {
	const colorBtn = card.querySelectorAll('.colour span');
	const colorImg = card.querySelectorAll('.imgBox img');
	const sizebtn = card.querySelectorAll(' .size input ');
	const buttonSize = card.querySelector('.buttons .buttons-size');
	const buttonColor = card.querySelectorAll('.buttons-color');
	const msg = card.querySelector('.contentBox .danger');
	const msgBg = document.querySelector('.msg');
	const msg2 = document.querySelector('.danger1');
	const msgBorder = card.querySelector('.size .getsize');

	sizebtn.forEach((btn) => {

		btn.addEventListener('click', () => {
			sizebtn.forEach((btn) => {
				btn.classList.remove('selectedSize');
			})
			if (msg != null && msgBorder != null) {
				msg.innerText = "";
				msgBg.style.display = "none";
				msg2.innerText = "";
				msgBorder.style.border = "none";
			}
			buttonSize.value = btn.value;
			btn.classList.add('selectedSize');
			updateQuantity(card, buttonSize, buttonColor);
			removeSize(card);

		})
	})
	buttonColor[0].value = colorBtn[0].classList.item(1);
	buttonColor[1].value = colorBtn[0].classList.item(1);
	colorBtn.forEach((btn, index) => {

		btn.addEventListener('click', () => {
			colorBtn.forEach((otherBtn) => {
				otherBtn.classList.remove('selected');
			});
			buttonColor[0].value = btn.classList.value;
			buttonColor[1].value = btn.classList.value;
			btn.classList.add('selected');
			colorImg.forEach((img) => {
				img.style.display = 'none';
			});
			colorImg[index].style.display = 'block';
			updateQuantity(card, buttonSize, buttonColor);
			removeSize(card);
		});
	});


});

function addSize() {
	const cards = document.querySelectorAll('.card');
	cards.forEach((card) => {
		const btns = card.querySelectorAll('.btn');
		const buttonSize = card.querySelector('.buttons .buttons-size');
		const getSize = card.querySelector('.size .size-border');
		const msg = card.querySelector('.contentBox .sizeMsg');
		const quantity = card.querySelector('.quantity');

		btns.forEach((btn, index) => {

			btn.addEventListener('click', () => {
				if (buttonSize.value === "0") {
					getSize.classList.add('getsize');
					msg.innerText = "Please select a size.";
					msg.classList.add('danger');
				} else {
					if (index === 0) {
						decrement(card, quantity);
					} else {
						increment(card, quantity);
					}

				}
			});
		});

	});
}
addSize();
function decrement(card, quantity) {

	const BtnQuantity = card.querySelector('.buttons-quantity');
	const quantityMsg = card.querySelector('.quantityMsg');

	if (quantity === null) {

		if (quantityMsg.innerText === '') {
			quantityMsg.innerText = '';
		} else if (quantityMsg.innerText === '1') {
			quantityMsg.innerText = '1';
		}
		else {
			quantityMsg.innerText--;
			quantityMsg.classList.add('quantity');
			BtnQuantity.value = quantityMsg.innerText;
		}
	} else {
		if (quantity.innerText === '1') {
			quantity.innerText = '1';
			BtnQuantity.value = quantity.innerText;
		} else if (quantity.innerText === '0') {
			quantity.innerText = '0';
		} else {
			quantity.innerText--;
			BtnQuantity.value = quantity.innerText;
		}
	}
}

function increment(card, quantity) {
	const BtnQuantity = card.querySelector('.buttons-quantity');
	const prodQuantity = card.querySelector('.prodQuantity');
	const quantityMsg = card.querySelector('.quantityMsg');


	if (quantity === null) {
		quantityMsg.classList.add('quantity');
		if (quantityMsg.innerText === prodQuantity.value) {
			quantityMsg.innerText = prodQuantity.value;

		} else {
			quantityMsg.innerText++;

		}
		BtnQuantity.value = quantityMsg.innerText;
	} else {

		if (quantity.innerText === prodQuantity.value) {
			quantity.innerText = prodQuantity.value;

		} else {
			quantity.innerText++;

		}
		BtnQuantity.value = quantity.innerText;
	}


}

function removeSize(card) {
	const getSize = card.querySelector('.size .size-border');
	const msg = card.querySelector('.contentBox .sizeMsg');
	getSize.classList.remove('getsize');
	msg.innerText = "";
	msg.classList.remove('danger');

}


const body = document.querySelector('.overlay');
const popup = document.querySelector('.popup');
const close = document.querySelector('.popup-top i');

if (popup != null) {

	body.style.opacity = '1';
	body.style.visibility = 'visible';
	setTimeout(() => {
		popup.classList.add('popup-animation');
	}, 300);
	close.addEventListener('click', () => {
		body.style.opacity = '0';
		body.style.visibility = 'hidden';
		popup.classList.remove('popup-animation');
	});
	body.addEventListener('click', () => {
		body.style.opacity = '0';
		body.style.visibility = 'hidden';
		popup.classList.remove('popup-animation');
	});
	setTimeout(function() {
		body.style.opacity = '0';
		body.style.visibility = 'hidden';
		popup.classList.remove('popup-animation');
	}, 5000);
}


const buttons = document.querySelectorAll('.slider-btns .box1 i');
const productContainer = document.querySelector('.card');
const imgCard = document.querySelector('.cards-section-wrapper');
const width = document.querySelector('.card').offsetWidth;
console.log(buttons);
buttons.forEach(button => {
	button.addEventListener('click', () => {
		if (button.id === "left") {
			console.log(button.id);
			imgCard.scrollLeft += -width - 40;
			console.log(imgCard.scrollLeft);

		} else {
			console.log(button.id);
			imgCard.scrollLeft += width + 40;
			console.log(imgCard.scrollLeft);
		}
	});

});

const nav = document.querySelector('.main-nav');
let scroll = 0;
window.addEventListener('scroll', () => {
	console.log(window.scrollY);
	if (window.scrollY > scroll) {
		nav.classList.add('scrolled');
	} else {
		nav.classList.remove('scrolled');
	}
	scroll = window.scrollY;
})

const body1 = document.querySelector('.body');
const loginPopup = document.querySelector('.login-popup');
const loginClose = document.querySelector('.info i');
if (loginPopup != null) {
	body1.style.opacity = '1';
	body1.style.visibility = 'visible';
	loginPopup.classList.add('details-zoom-in');
	loginPopup.style.opacity = '1';
	loginPopup.style.visibility = 'visible';

	loginClose.addEventListener('click', () => {
		body1.style.opacity = '0';
		body1.style.visibility = 'hidden';
		loginPopup.classList.remove('details-zoom-in');
		setTimeout(() => {
			loginPopup.style.opacity = '0';
			loginPopup.style.visibility = 'hidden';
		}, 300);
	});

	body1.addEventListener('click', () => {
		body1.style.opacity = '0';
		body1.style.visibility = 'hidden';
		loginPopup.classList.remove('details-zoom-in');
		setTimeout(() => {
			loginPopup.style.opacity = '0';
			loginPopup.style.visibility = 'hidden';
		}, 300);

	});

}



const login = document.querySelector('.login');
const inputArea = document.querySelectorAll('.input-area');
const label = document.querySelectorAll('.input-field label');
const p = document.querySelectorAll('.input-field p');


inputArea.forEach((input, i) => {
	input.addEventListener('input', () => {
		if (input.value === "") {
			input.classList.add('danger');
			label[i].classList.add('danger');
			p[i].innerText = "Required";
			p[i].classList.add('required');
		} else {
			input.classList.remove('danger');
			label[i].classList.remove('danger');
			if (input.id === "email") {
				const ar = input.value.split('');
				if (ar.includes("@") && ar.includes(".")) {
					const index = ar.indexOf("@");
					if (ar.indexOf("@") >= 3 && ar.indexOf(".") >= index + 3) {
						p[i].innerText = "";
						p[i].classList.remove('required');
					} else {
						p[i].innerText = "Invalid email address";
						p[i].classList.add('required');
					}
				} else {
					p[i].innerText = "Invalid email address";
					p[i].classList.add('required');
				}

			} else if (input.id === "password") {
				const ar = input.value.split('');
				let uppercase = false;
				let lowercase = false;
				let digit = false;
				let symbol = false;
				for (let i = 0; i < ar.length; i++) {
					if (ar[i] >= 'A' && ar[i] <= 'Z') {
						uppercase = true;
					} else if (ar[i] >= 'a' && ar[i] <= 'z') {
						lowercase = true;
					} else if (ar[i] >= '0' && ar[i] <= '9') {
						digit = true;
					} else {
						symbol = true;
					}
				}
				if (ar.length >= 6 && ar.length <= 13) {
					if (uppercase && lowercase && digit && symbol) {
						p[i].innerText = "";
						p[i].classList.remove('required');
					} else {
						p[i].innerText = "It must contain at least one lowercase letter (a-z), 1 uppercase letter (A-Z), 1 numeric digit (0-9), and 1  special characters (@, $)";
						p[i].classList.add('required');
					}
				} else {
					p[i].innerText = "Password must contain: minimum 6 and maximum 13 characters";
					p[i].classList.add('required');
				}

			}
		}
	});

	login.addEventListener('click', () => {
		if (input.value === "") {
			label[i].classList.add('danger');
			p[i].innerText = "Required";
			p[i].classList.add('required');
		} else if (p[0].innerText === "" && p[1].innerText === "") {
			login.type = "submit";
		}

	})

});

const loginMsg = document.querySelector('.login-msg');
if (loginMsg != null) {
	setTimeout(() => {
		loginMsg.style.display = "none";
	}, 3000);
}


const userDetails = document.querySelector(".user-details");
const userBox = document.querySelector(".user-box");
const arrowBtns = document.querySelectorAll(".box i")
const firstCardWidth = userDetails.querySelector(".user-box").offsetWidth;
const userDetailsChlidrens = [...userDetails.children];
let isDragging = false, startX, startScrollLeft, timeoutId;

// For the buttons
// Add event listeners for the arrow buttons to scroll the userdetails
arrowBtns.forEach(btn => {
	btn.addEventListener("click", () => {
		// console.log(btn.id);
		userDetails.scrollLeft += btn.id === "left" ? -firstCardWidth : firstCardWidth;
		console.log(userDetails.scrollLeft);
	})
});

// For the Dragging feature with infinte scroll

// 2) This start the dragging of the card with the values of dragging()
const dragStart = (e) => {
	isDragging = true;
	userDetails.classList.add("dragging");
	// Records the initial cursor and scroll position of the carousel   
	startX = e.pageX;
	startScrollLeft = userDetails.scrollLeft;
}

// 1) This will going to record the card container by the scrollLeft with the help of addeventlistener of mousemove
const dragging = (e) => {
	if (!isDragging) {
		return;
	}
	// updates the scroll position of the carousel based on the carousel based on the cursoel movement
	userDetails.scrollLeft = startScrollLeft - (e.pageX - startX);
}

const dragStop = () => {
	isDragging = false;
	userDetails.classList.remove("dragging");
}

const autoPlay = () => {
	if (window.innerWidth < 800) return; // Reuturn if window is smaller than 800
	// Autoplay the userdetails after every 2500ms
	timeoutId = setTimeout(() => userDetails.scrollLeft += firstCardWidth, 2500);
}
autoPlay();

// For infinite scroll
//Get the numbers of cards that can fit in the carousel at once

let cardPerView = Math.round(userDetails.offsetWidth / firstCardWidth);

// Insert copies of the last few cards to beginning of userdetails for infinte scrolling
userDetailsChlidrens.slice(-cardPerView).reverse().forEach(card => {
	userDetails.insertAdjacentHTML("afterbegin", card.outerHTML);
});

//Insert copies of the last few cards to end of userdetails for infinte scrolling
userDetailsChlidrens.slice(0, cardPerView).forEach(card => {
	userDetails.insertAdjacentHTML("beforeend", card.outerHTML);
});
const infiniteScroll = () => {
	// If the carousel is at the beginning, scroll to the end
	if (userDetails.scrollLeft === 0) {
		userDetails.classList.add("no-transition");
		userDetails.scrollLeft = userDetails.scrollWidth - (2 * userDetails.offsetWidth);
		userDetails.classList.remove("no-transition");

	}
	// If the carousel is at the end, scroll to the beginning
	else if (Math.ceil(userDetails.scrollLeft) === userDetails.scrollWidth - userDetails.offsetWidth) {
		userDetails.classList.add("no-transition");
		userDetails.scrollLeft = userDetails.offsetWidth;
		userDetails.classList.remove("no-transition");
	}

	// clear existing timeout & start autoplay if mouse is not hovering over userdetails
	clearTimeout(timeoutId);
	if (!userBox.matches(":hover")) autoPlay();
}


// Dragging Feature
userDetails.addEventListener("mousedown", dragStart);
userDetails.addEventListener("mousemove", dragging);
document.addEventListener("mouseup", dragStop); // document we can stop on any of the sections 

// InfiniteScroll Feature
userDetails.addEventListener("scroll", infiniteScroll);

// AutoPlay Feature
userDetails.addEventListener("mouseenter", () => clearTimeout(timeoutId));
userDetails.addEventListener("mouseleave", autoPlay);


	const navbar1 = document.querySelector('.nav-toggle');
	const navLink = document.querySelector('.nav-links');
	
	navbar1.addEventListener('click',() =>{
		navLink.classList.toggle('nav-links-active-top');
		
	});
