var events = [
    {'Date': new Date(2019, 10, 7), 'Title': 'Doctor appointment at 3:25pm.'},
    {'Date': new Date(2019, 10, 18), 'Title': 'New Garfield movie comes out!', 'Link': 'https://garfield.com'},
    {'Date': new Date(2019, 10, 27), 'Title': '25 year anniversary', 'Link': 'https://www.google.com.au/#q=anniversary+gifts'},
];

var settings = {
    Color: '',
    LinkColor: '',
    NavShow: true,
    NavVertical: false,
    NavLocation: '',
    DateTimeShow: true,
    DateTimeFormat: 'mmm, yyyy',
    DatetimeLocation: '',
    EventClick: '',
    EventTargetWholeDay: false,
    DisabledDays: []
    // ModelChange: model
};

var element = document.getElementById('caleandar');
caleandar(element, events, settings);
