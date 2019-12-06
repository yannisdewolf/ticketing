/*
  Author: Jack Ducasse;
  Version: 0.1.0;
  (◠‿◠✿)
*/
var Calendar = function (model, options, date) {

    // Default Values
    this.Options = {
        Color: '',
        LinkColor: '',
        NavShow: true,
        DateTimeFormat: 'mmm, yyyy',
        DatetimeLocation: '',
        EventClick: '',
        EventTargetWholeDay: false,
        DisabledDays: [],
        ModelChange: model
    };
    // Overwriting default values
    for (var key in options) {
        this.Options[key] = typeof options[key] == 'string' ? options[key].toLowerCase() : options[key];
    }

    model ? this.Model = model : this.Model = {};
    this.Today = moment();
    if (date) {
        this.Selected = date;
    } else {
        this.Selected = this.Today.clone();
    }
    this.FirstDayOfSelectedMonth = this.Selected.clone();
    this.FirstDayOfSelectedMonth.set('day', 1);


    this.Selected.LastDay = this.Selected.endOf('month');
    this.cloneOfToday = this.Today.clone();
    this.Prev = this.cloneOfToday.subtract(1, 'months');

};

function getFirstDayOfSelectedMonth(calendar) {
    return calendar.FirstDayOfSelectedMonth.clone();
}

function createCalendar(calendar, element, adjuster) {

    if (typeof adjuster !== 'undefined') {
        var firstDayOfSelectedMonth = getFirstDayOfSelectedMonth(calendar);
        // q.set('year', calendar.Selected.year());
        // q.set('month', calendar.Selected.month());
        // q.set('date', 1);

        firstDayOfSelectedMonth.add('month', adjuster);

        calendar = new Calendar(calendar.Model, calendar.Options, firstDayOfSelectedMonth);
        element.innerHTML = '';
    } else {
        for (var key in calendar.Options) {
            typeof calendar.Options[key] != 'function' && typeof calendar.Options[key] != 'object' && calendar.Options[key] ? element.className += " " + key + "-" + calendar.Options[key] : 0;
        }
    }
    var mainSection = document.createElement('div');
    mainSection.className += "cld-main";

    function addNavigation() {
        var navigation = document.createElement('div');
        navigation.className += "cld-datetime";
        if (calendar.Options.NavShow) {
            var previousMonth = document.createElement('div');
            previousMonth.className += " cld-rwd cld-nav";
            previousMonth.addEventListener('click', function () {
                createCalendar(calendar, element, -1);
            });
            previousMonth.innerHTML = '<svg height="15" width="15" viewBox="0 0 75 100" fill="rgba(0,0,0,0.5)"><polyline points="0,50 75,0 75,100"></polyline></svg>';
            navigation.appendChild(previousMonth);
        }
        var today = document.createElement('div');
        today.className += ' today';
        today.innerHTML = calendar.Selected.format('MMMM') + ", " + calendar.Selected.format('YYYY');
        navigation.appendChild(today);
        if (calendar.Options.NavShow) {
            var nextMonth = document.createElement('div');
            nextMonth.className += " cld-fwd cld-nav";
            nextMonth.addEventListener('click', function () {
                createCalendar(calendar, element, 1);
            });
            nextMonth.innerHTML = '<svg height="15" width="15" viewBox="0 0 75 100" fill="rgba(0,0,0,0.5)"><polyline points="0,0 75,50 0,100"></polyline></svg>';
            navigation.appendChild(nextMonth);
        }
        mainSection.appendChild(navigation);
    }

    function AddLabels() {
        var labels = document.createElement('ul');
        labels.className = 'cld-labels';
        var labelsList = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
        for (var i = 0; i < labelsList.length; i++) {
            var label = document.createElement('li');
            label.className += "cld-label";
            label.innerHTML = labelsList[i];
            labels.appendChild(label);
        }
        mainSection.appendChild(labels);
    }

    function AddDays() {
        // Create Number Element
        function DayNumber(n) {
            var wrapper = document.createElement('div');
            wrapper.className = "wrapper";

            var number = document.createElement('div');
            number.className += "cld-number";
            number.className += " marked";
            number.innerHTML += n;


            wrapper.appendChild(number);
            return wrapper;
        }

        var days = document.createElement('ul');
        days.className += "cld-days";
        // Previous Month's Days
        var dayNumberOfFirstDayOfCurrentMonth = (calendar.Selected.startOf('month').get('day') + 6) % 7;
        for (var dayNumber = 0; dayNumber < dayNumberOfFirstDayOfCurrentMonth; dayNumber++) {
            var dayElement = document.createElement('li');
            dayElement.className += "cld-day prevMonth";
            //Disabled Days
            var d = dayNumber % 7;
            for (var q = 0; q < calendar.Options.DisabledDays.length; q++) {
                if (d == calendar.Options.DisabledDays[q]) {
                    dayElement.className += " disableDay";
                }
            }

            var number = DayNumber(calendar.Prev.daysInMonth() - dayNumberOfFirstDayOfCurrentMonth + dayNumber + 1);
            dayElement.appendChild(number);

            days.appendChild(dayElement);
        }
        // Current Month's Days
        for (var dayNumber = 0; dayNumber < calendar.Selected.daysInMonth(); dayNumber++) {
            var dayElement = document.createElement('li');
            dayElement.className += "cld-day currMonth";
            //Disabled Days
            var d = (dayNumber + 1) % 7;
            for (var q = 0; q < calendar.Options.DisabledDays.length; q++) {
                if (d === calendar.Options.DisabledDays[q]) {
                    dayElement.className += " disableDay";
                }
            }
            var number = DayNumber(dayNumber + 1);
            // Check Date against Event Dates
            for (var elementIndex = 0; elementIndex < calendar.Model.length; elementIndex++) {
                var evDate = moment(calendar.Model[elementIndex].dateString);
                var currentlySelectedDay = calendar.Selected.clone();
                currentlySelectedDay.set('date', dayNumber+1);

                if (evDate.isSame(currentlySelectedDay, 'day')) {
                    number.className += " eventday";
                    var title = document.createElement('span');
                    title.className += "cld-title";
                    title.style = 'background: ' + calendar.Model[elementIndex].color;
                    if (typeof calendar.Model[elementIndex].Link == 'function' || calendar.Options.EventClick) {
                        var a = document.createElement('a');
                        a.setAttribute('href', '#');
                        a.innerHTML += calendar.Model[elementIndex].title;
                        if (calendar.Options.EventClick) {
                            var z = calendar.Model[elementIndex].Link;
                            if (typeof calendar.Model[elementIndex].Link != 'string') {
                                a.addEventListener('click', calendar.Options.EventClick.bind.apply(calendar.Options.EventClick, [null].concat(z)));
                                if (calendar.Options.EventTargetWholeDay) {
                                    dayElement.className += " clickable";
                                    dayElement.addEventListener('click', calendar.Options.EventClick.bind.apply(calendar.Options.EventClick, [null].concat(z)));
                                }
                            } else {
                                a.addEventListener('click', calendar.Options.EventClick.bind(null, z));
                                if (calendar.Options.EventTargetWholeDay) {
                                    dayElement.className += " clickable";
                                    dayElement.addEventListener('click', calendar.Options.EventClick.bind(null, z));
                                }
                            }
                        } else {
                            a.addEventListener('click', calendar.Model[elementIndex].Link);
                            if (calendar.Options.EventTargetWholeDay) {
                                dayElement.className += " clickable";
                                dayElement.addEventListener('click', calendar.Model[elementIndex].Link);
                            }
                        }
                        title.appendChild(a);
                    } else {
                        title.innerHTML += calendar.Model[elementIndex].title;
                    }
                    number.appendChild(title);
                }
            }
            dayElement.appendChild(number);
            // If Today..
            // if((i+1) == calendar.Today.getDate() && calendar.Selected.Month == calendar.Today.Month && calendar.Selected.Year == calendar.Today.Year){
            var iPlusOne = dayNumber + 1;
            var dayNumberOfToday = calendar.Today.get('date');
            // var currentlyHandlingToday = iPlusOne === dayNumberOfToday;
            // var selectedDayIsToday = calendar.Selected.isSame(calendar.Today, 'day');

            var currentDayInSelectedMonth = calendar.Selected.clone();
            currentDayInSelectedMonth.set('date', moment().date());

            if (dayNumberOfToday === iPlusOne && currentDayInSelectedMonth.isSame(moment(), 'day')) {
                dayElement.className += " today";
            }
            days.appendChild(dayElement);
        }
        var extraDays = 7 - days.children.length%7;
        if(extraDays < 7) {
            for (var dayNumber = 0; dayNumber < extraDays; dayNumber++) {
                var dayElement = document.createElement('li');
                dayElement.className += "cld-day nextMonth";
                var number = DayNumber(dayNumber + 1);
                dayElement.appendChild(number);
                days.appendChild(dayElement);
            }
        }
        mainSection.appendChild(days);
    }

    if (calendar.Options.Color) {
        mainSection.innerHTML += '<style>.cld-main {color:' + calendar.Options.Color + ';}</style>';
    }
    if (calendar.Options.LinkColor) {
        mainSection.innerHTML += '<style>.cld-title a{color:' + calendar.Options.LinkColor + ';}</style>';
    }
    element.appendChild(mainSection);

    if (calendar.Options.NavShow) {
        addNavigation();
    }
    AddLabels();
    AddDays();
}

function caleandar(el, data, settings) {
    var obj = new Calendar(data, settings);
    createCalendar(obj, el);
}
