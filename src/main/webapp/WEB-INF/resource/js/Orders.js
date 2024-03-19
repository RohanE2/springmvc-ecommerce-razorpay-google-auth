	const nav = document.querySelector('.main-nav');
			let scroll = 0;
			window.addEventListener('scroll',() =>{
				console.log(window.scrollY);
				if(window.scrollY > scroll){
					nav.classList.add('scrolled');
				}else{
					nav.classList.remove('scrolled');
				}
			scroll = window.scrollY;
			})
			
			
			const dateInput = document.querySelectorAll('.date-input');
		dateInput.forEach((input,i) =>{
			const currentDate = new Date(input.value);
			currentDate.setDate(currentDate.getDate() + 7);
			
			const lastDate = new Date();
			lastDate.setDate(currentDate.getDate() + 20);
			
			const fromDate = currentDate.toLocaleDateString('en-US', { weekday: 'short', day: 'numeric', month: 'short' });
			const toDate = lastDate.toLocaleDateString('en-US', { weekday: 'short', day: 'numeric', month: 'short' });

			const arrivingDate = document.querySelectorAll('.date-text');
			arrivingDate[i].textContent = "Arrives " + fromDate + " - " + toDate;
		})
		
const body = document.querySelector('.body');
const payment = document.querySelector('.payment');

if (payment != null) {
	body.style.opacity = '1';
	body.style.visibility = 'visible';
	payment.classList.add('details-zoom-in');
	payment.style.opacity = '1';
	payment.style.visibility = 'visible';

	body.addEventListener('click', () => {
		body.style.opacity = '0';
		body.style.visibility = 'hidden';
		payment.classList.remove('details-zoom-in');
		setTimeout(() => {
			payment.style.opacity = '0';
			payment.style.visibility = 'hidden';
		}, 300);

	});

}

const navbar = document.querySelector('.nav-toggle');
const navLink = document.querySelector('.nav-links');

navbar.addEventListener('click',() =>{
	navLink.classList.toggle('nav-links-active-top');
	
});