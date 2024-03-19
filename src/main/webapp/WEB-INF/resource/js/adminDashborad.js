    const sideMenu = document.querySelector("aside");
    const menuBtn = document.querySelector("#menu-btn");
    const closeBtn = document.querySelector("#close-btn");
    const themeToggler = document.querySelector(".theme-toggler");


    // show sidebar
    menuBtn.addEventListener('click',() =>{
        sideMenu.style.display = 'block';
    })

    // close sidebar
    closeBtn.addEventListener('click',() =>{
        sideMenu.style.display = 'none';
    })

    //change theme
    themeToggler.addEventListener('click',() =>{
        document.body.classList.toggle('dark-theme-variables');

    // themeToggler.querySelector('span').classList.toggle('active');

        themeToggler.querySelector('span:nth-child(1)').classList.toggle('active');
        themeToggler.querySelector('span:nth-child(2)').classList.toggle('active');
    })
    
   	const detailInfo = document.querySelector('.detail-info');
    const overlay = document.querySelector('.overlay');
    const closeIcon = document.querySelector('.close-icon');
   
    if(detailInfo != null){
    	overlay.style.opacity = '1';
    	overlay.style.visibility = 'visible';
    	detailInfo.style.opacity = '1';
    	detailInfo.style.visibility = 'visible';
    	detailInfo.style.transform = 'translate(-50%, -50%) scale(1)';
    	
    	closeIcon.addEventListener('click',() =>{
    		overlay.style.opacity = '0';
        	overlay.style.visibility = 'hidden';
        	detailInfo.style.opacity = '0';
        	detailInfo.style.visibility = 'hidden';
        	detailInfo.style.transform = 'translate(-50%, -50%) scale(0)';
  
    	})
    	overlay.addEventListener('click',() =>{
    		overlay.style.opacity = '0';
        	overlay.style.visibility = 'hidden';
        	detailInfo.style.opacity = '0';
        	detailInfo.style.visibility = 'hidden';
        	detailInfo.style.transform = 'translate(-50%, -50%) scale(0)';
  
    	})
    }
    
    const sales = document.querySelector('.sales .number p');
 	const salesPer = parseInt(sales.textContent);
 	const offSetValue = 225 - 225 *(salesPer/100);
 	 document.documentElement.style.setProperty('--final-offset', offSetValue);
 	 
 	const income = document.querySelector('.income .number p');
 	const incomePer = parseInt(income.textContent);
 	const offSetValueI = 225 - 225 *(incomePer/100);
 	 document.documentElement.style.setProperty('--final-offsetI', offSetValueI);
 	 
 	 const number = document.querySelectorAll('.number p');
 	 
 	 number.forEach((value,i)=>{
 		let counter = 0;
 		const target = parseInt(value.innerText);

 			const intervalId = setInterval(()=>{
 				if(counter === target){
 					clearInterval(intervalId);
 				}else{
 				value.innerText = ++counter + "%";
 				}	
 				
 			},30)
 	 })
 	 
 	 
		const recentOrder = document.querySelector('main .recent-orders .recent-wrapper');
		const showAll = document.querySelector('.showAll');
		
		showAll.addEventListener('click', () => {
			console.log(recentOrder.style.height);
			if(recentOrder.style.height === '100%'){
				   recentOrder.style.height = '36vh';
				    showAll.innerText = 'Show All';
			 
			}else{
				  recentOrder.style.height = '100%';
				   showAll.innerText = 'Show less';
			}
		});

		const addProductBtn = document.querySelectorAll('.add-product');
		const customer = document.querySelector('.customer');
		const dassboard = document.querySelector('.dassboard');
		const addProduct = document.querySelector('.main-add-products')
		addProductBtn.forEach((add,i) =>{
			
			add.addEventListener('click',() =>{
				dassboard.style.display = 'none';
				addProduct.style.display = 'block';
				if(i === 1){
					sideBar.forEach((otherBar) => {
		                otherBar.classList.remove('active');
		        });
					addProductBtn[0].classList.add('active');
				}
				
			})
		})
		
		customer.addEventListener('click',() =>{
			dassboard.style.display = 'block';
			addProduct.style.display = 'none';
			
			
		})
		const sideBar = document.querySelectorAll('.sidebar a');
		sideBar.forEach((bar)=>{
			
			bar.addEventListener('click',()=>{
				
				sideBar.forEach((otherBar) => {
	                otherBar.classList.remove('active');
	        });
				bar.classList.toggle('active');
				
			})
			
		})