var tools = {
	decodeUrlParams : function(search) {
		var params = {}, //
		kvs = (search || location.search).replace(/^[?#]/, '').replace(/[;&]$/, '').replace(/[+]/g, ' ').split(/[&;]/);
		for (var i = 0; i < kvs.length; i++) {
			var kv = kvs[i].split('='), //
			k = decodeURIComponent(kv[0] || ''), //
			v = decodeURIComponent(kv[1] || '');
			if (k.length || v.length) {
				params[k] = v;
			}
		}
		return params;
	}
};