import sys

if len(sys.argv) < 2:
    print 'Usage:<input>'
    exit(-1)

fmap = {}
fi =open(sys.argv[1])
for l in fi:    
    arr = l.split(',')
    sr = int( arr[-2] )
    if sr not in fmap:
        fmap[sr] = 1
    else:
        fmap[sr] += 1
fi.close()

st = sorted( fmap.items(), key=lambda i:-i[1] )

for k,v in st:
    print '%d\t%d' %(k,v)



